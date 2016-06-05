package com.fias.web.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.lang.WordUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.fias.web.controllers.LoginController.SAXHandler;
import com.fias.web.dao.Address;
import com.fias.web.dao.DatabaseConfig;
import com.fias.web.dao.FormValidationGroup;
import com.fias.web.dao.MyError;
import com.fias.web.dao.User;
import com.fias.web.dao.UsersDao;
import com.fias.web.data.*;
import com.fias.web.service.AddressService;
import com.fias.web.service.UsersService;

@Configuration
@EnableScheduling
@Controller
@WebServlet("/logincontroller")
public class LoginController extends HttpServlet implements SchedulingConfigurer {

	private static final long serialVersionUID = 1L;
	private int logicForFull = 1;
	private int mechanical = 0;
	private String fullLoadFinished = "1";
	private int successUpdateDb = 1;
	private int successFullUpdateDb = 1;

	ScheduledTaskRegistrar _taskRegistrar;
	ExecutorService threadPoolExecutor;
	Future<?> future = null;

	private int updateInt = 0;

	private String verCheck = null;
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	private static String hh = "00";
	private static String mm = "00";
	private static String ss = "00";

	// private static final int fixedRateNumber = 30000;

	@Autowired
	private SessionFactory sessionFactory;

	public Session session() {
		return sessionFactory.getCurrentSession();
	}

	private UsersService usersService;
	private AddressService addressService;

	@Autowired
	public void setUsersService(UsersService usersService) {
		this.usersService = usersService;
	}

	@Autowired
	public void setAddressService(AddressService addressService) {
		this.addressService = addressService;
	}

	@Autowired
	Environment env;

	@Bean(destroyMethod = "shutdown")
	public Executor taskExecutor() {
		return Executors.newScheduledThreadPool(100);
	}

	@RequestMapping("/login")
	public String showLogin() {
		return "login";
	}

	@RequestMapping("/denied")
	public String showDenied() {

		return "denied";
	}

	@RequestMapping("/admin")
	public String showAdmin(Model model) {

		List<DatabaseConfig> config = addressService.getCurrentConfig();
		model.addAttribute("databaseconfig", config);
		return "admin";
	}

	@RequestMapping(value = "/adminloadupdate", method = RequestMethod.POST)
	public String adminLoadUpdate(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam(value = "updatedatabase", required = false) String updatedatabase) {
		successUpdateDb = 1;
		mechanical = 1;
		logicForFull = 0;
		_taskRegistrar.setScheduler(taskExecutor());

		addressService.updateStatusSet("Обновление Начато");

		List<DatabaseConfig> config = addressService.getCurrentConfig();
		request.setAttribute("databaseconfig", config);
		try {
			request.getRequestDispatcher("/WEB-INF/tiles/admin.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String httpAddress = addressService.getHttpAddress();
		// URL url =
		// this.getClass().getClassLoader().getResource("application.properties");
		// System.out.println(url.getPath());
		// File file = new File(url.getFile());
		// System.out.println(file);
		//
		String fileURL = httpAddress + "fias_delta_xml.rar";
		String saveDir = "C:\\java\\spring\\workspace\\fias_v_60_all_new\\data\\";
		String deletePathRar = "C:\\java\\spring\\workspace\\fias_v_60_all_new\\data\\fias_delta_xml.rar";
		String deletePathXml = "C:\\java\\spring\\workspace\\fias_v_60_all_new\\data\\xml\\";

		try {
			addressService.updateStatusSet("Загрузка обновления");

			List<DatabaseConfig> config2 = addressService.getCurrentConfig();
			request.setAttribute("databaseconfig", config2);
			try {
				request.getRequestDispatcher("/WEB-INF/tiles/admin.jsp").forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			HttpDownloadFile.downloadFile(fileURL, saveDir);
			String filename = "C:\\java\\spring\\workspace\\fias_v_60_all_new\\data\\fias_delta_xml.rar";
			String extractDir = "C:\\java\\spring\\workspace\\fias_v_60_all_new\\data\\xml\\";

			addressService.updateStatusSet("Распаковка Архива");

			List<DatabaseConfig> config4 = addressService.getCurrentConfig();
			request.setAttribute("databaseconfig", config4);
			try {
				request.getRequestDispatcher("/WEB-INF/tiles/admin.jsp").forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			RarArchive.extract(filename, extractDir);

			String xmlPath = "C:\\java\\spring\\workspace\\fias_v_60_all_new\\data\\xml\\";
			addressService.updateStatusSet("Вставка в базу");

			List<DatabaseConfig> config5 = addressService.getCurrentConfig();
			request.setAttribute("databaseconfig", config5);
			try {
				request.getRequestDispatcher("/WEB-INF/tiles/admin.jsp").forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {

				File f = new File(xmlPath); // mb change for one
				File[] matchingFiles = f.listFiles(new FilenameFilter() {
					public boolean accept(File dir, String name) {
						return name.startsWith("AS_ADDROBJ");
					}
				});

				String name = matchingFiles[0].getName();
				deletePathXml = deletePathXml + name;

				SAXParserFactory factory = SAXParserFactory.newInstance();
				SAXParser parser = factory.newSAXParser();

				FileInputStream is = new FileInputStream(xmlPath + name);
				DefaultHandler handler = new SAXHandler();
				parser.parse(is, handler); // parse from xml to db
			} catch (Exception e) {
				successUpdateDb = 0;
				System.out.println("error while extracting or parsing");
				e.printStackTrace();
			}

		} catch (IOException ex) {
			addressService.updateStatusSet("Ошибка Обновления");

			List<DatabaseConfig> config6 = addressService.getCurrentConfig();
			request.setAttribute("databaseconfig", config6);
			try {
				request.getRequestDispatcher("/WEB-INF/tiles/admin.jsp").forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			successUpdateDb = 0;
			deletePathXml = null;
			ex.printStackTrace();
		} finally {
			RemoveFile.delete(deletePathRar);
			RemoveFile.delete(deletePathXml);

			if (successUpdateDb == 1) {
				System.out.println("success");
				String httpAddress2 = addressService.getHttpAddress();
				String fileURL2 = httpAddress2 + "VerDate.txt";
				Scanner s = null;
				try {
					URL url2 = new URL(fileURL2);
					s = new Scanner(url2.openStream());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				verCheck = s.next().trim();
				addressService.databaseSetDateUpdate(verCheck);
			}
		}
		System.out.println("finish update");
		mechanical = 0;

		if (successUpdateDb == 1) {
			addressService.updateStatusSet("Обновление Завершено");

			List<DatabaseConfig> config3 = addressService.getCurrentConfig();
			request.setAttribute("databaseconfig", config3);
			try {
				request.getRequestDispatcher("/WEB-INF/tiles/admin.jsp").forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return "admin";
	}

	@RequestMapping(value = "/adminloadfullupdate", method = RequestMethod.POST)
	public String adminLoadFullUpdate(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam(value = "loadfulldatabase", required = false) String loadfulldatabase) {
		logicForFull = 1;
		mechanical = 1;

		addressService.updateStatusSet("Идет дроп базы адресов");

		List<DatabaseConfig> config = addressService.getCurrentConfig();
		request.setAttribute("databaseconfig", config);
		try {
			request.getRequestDispatcher("/WEB-INF/tiles/admin.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Format`s started");
		addressService.databaseFullUpdateSetZero();
		addressService.databaseAddressDrop();
		System.out.println("Drop succefull");

		String httpAddress = addressService.getHttpAddress();
		String fileURL = httpAddress + "fias_xml.rar";
		// ABSOLUTE!!!!!!!!!!!!PATH! you have to change it
		String saveDir = "C:\\java\\spring\\workspace\\fias_v_60_all_new\\data\\";
		String deletePathRar = "C:\\java\\spring\\workspace\\fias_v_60_all_new\\data\\fias_xml.rar";
		String deletePathXml = "C:\\java\\spring\\workspace\\fias_v_60_all_new\\data\\fias_xml\\";

		addressService.updateStatusSet("Загрузка всей базы");

		List<DatabaseConfig> config2 = addressService.getCurrentConfig();
		request.setAttribute("databaseconfig", config2);
		try {
			request.getRequestDispatcher("/WEB-INF/tiles/admin.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			try {
				HttpDownloadFile.downloadFile(fileURL, saveDir);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			String filename = "C:\\java\\spring\\workspace\\fias_v_60_all_new\\data\\fias_xml.rar";
			String extractDir = "C:\\java\\spring\\workspace\\fias_v_60_all_new\\data\\fias_xml\\";
			addressService.updateStatusSet("Распаковка всей базы");

			List<DatabaseConfig> config3 = addressService.getCurrentConfig();
			request.setAttribute("databaseconfig", config3);
			try {
				request.getRequestDispatcher("/WEB-INF/tiles/admin.jsp").forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			RarArchive.extract(filename, extractDir);
			addressService.updateStatusSet("Вставка всей базы");

			List<DatabaseConfig> config4 = addressService.getCurrentConfig();
			request.setAttribute("databaseconfig", config4);
			try {
				request.getRequestDispatcher("/WEB-INF/tiles/admin.jsp").forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String xmlPath = "C:\\java\\spring\\workspace\\fias_v_60_all_new\\data\\fias_xml\\";

			try {

				File f = new File(xmlPath); // mb change for one
				File[] matchingFiles = f.listFiles(new FilenameFilter() {
					public boolean accept(File dir, String name) {
						return name.startsWith("AS_ADDROBJ");
					}
				});

				String name = matchingFiles[0].getName();
				deletePathXml = deletePathXml + name;

				SAXParserFactory factory = SAXParserFactory.newInstance();
				SAXParser parser = factory.newSAXParser();

				FileInputStream is = new FileInputStream(xmlPath + name);
				DefaultHandler handler = new SAXHandler();
				parser.parse(is, handler);
			} catch (Exception e) {
				// SuccessFullLoad = 0;
				addressService.updateStatusSet("Ошибка при загрузке всей базы");
				List<DatabaseConfig> config5 = addressService.getCurrentConfig();
				request.setAttribute("databaseconfig", config5);
				try {
					request.getRequestDispatcher("/WEB-INF/tiles/admin.jsp").forward(request, response);
				} catch (ServletException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				successFullUpdateDb = 0;
				e.printStackTrace();
			}

		} finally {
			RemoveFile.delete(deletePathRar);
			RemoveFile.delete(deletePathXml);
		}
		if (successFullUpdateDb == 1) {
			addressService.databaseFullUpdateSetOne();
		} else {
			System.out.println("Full Update hasnt completed");
		}
		System.out.println("finish");
		mechanical = 0;

		if (successFullUpdateDb == 1) {
			addressService.updateStatusSet("Полная Загрузка прошла успешно");

			List<DatabaseConfig> config7 = addressService.getCurrentConfig();
			request.setAttribute("databaseconfig", config7);
			try {
				request.getRequestDispatcher("/WEB-INF/tiles/admin.jsp").forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "admin";
	}

	@RequestMapping(value = "/changeaddress", method = RequestMethod.GET)
	@ResponseBody
	public View adminChangeAddress(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam(value = "httpaddress") String httpAddress) {
		addressService.databaseSetHttpAddress(httpAddress);
		return new RedirectView("admin");
	}

	@RequestMapping(value = "/refreshtime", method = RequestMethod.GET)
	@ResponseBody
	public View adminRefreshTime(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam(value = "updatetime") String updateTime) {
		addressService.databaseSetUpdateTime(updateTime);
		updateInt = 1;
		configureTasks(_taskRegistrar);
		return new RedirectView("admin");
	}	
	
	@RequestMapping(value = "/getconfig", method = RequestMethod.GET)
	public String getConfig(HttpServletRequest request, HttpServletResponse response, Model model) {
		List<DatabaseConfig> config = addressService.getCurrentConfig();
		System.out.println("myconfig");
		request.setAttribute("databaseconfig", config);
		try {
			request.getRequestDispatcher("/WEB-INF/tiles/admin.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "admin";
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String showAddresses(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam(value = "formalname", required = false) String getaddress,
			@RequestParam(value = "formalnameexact", required = false) String formalnameexact,
			@RequestParam(value = "code", required = false) String code,
			@RequestParam(value = "postalcode", required = false) String postalcode,
			@RequestParam(value = "okato", required = false) String okato,
			@RequestParam(value = "oktmo", required = false) String oktmo,
			@RequestParam(value = "regioncode", required = false) String regioncode) {

		String _formalnameexact = formalnameexact;
		String _code = code;
		String _postalcode = postalcode;
		String _okato = okato;
		String _oktmo = oktmo;
		String _regioncode = regioncode;

		System.out.println(_formalnameexact);
		System.out.println(_code);
		System.out.println(_postalcode);

		final ArrayList<Address> addresses = new ArrayList<Address>();
		ArrayList<String> deleteTypes = new ArrayList<String>();

		if (_formalnameexact.equals("") && _code.equals("") && _postalcode.equals("") && _okato.equals("")
				&& _oktmo.equals("") && _regioncode.equals("")) {

			ArrayList<Address> restrictedAddressesByTen = new ArrayList<Address>();
			String value = getaddress;
			String s = value;

			if (s.indexOf("-") != -1) {
				s = s.replaceAll("-", " ");
			} else {
				System.out.println("there is no '-' in the string");
			}

			s = WordUtils.capitalize(s);

			List<String> myList = new ArrayList<String>(
					Arrays.asList(s.replaceAll("[\\p{P}&&[^-]]", "").split("\\s+")));
			String addressTypes = "Город Край Область Округ Республика Поселение Район Улус Волость Поселок Массив Отделение Администрация Муниципальное Образование Поселение Сельсовет Территория \n"
					+ "Район Территория Аал Автодорога Арбан Аул Волость Выселки Город Городок Деревня Будка Казарма ж/д Остановка Платформа Пост Разъезд Станция \n"
					+ "Зона Заимка Квартал Кордон Леспромхоз Местечко Массив Микрорайон Пункт Остров Погост Починок Разъезд Село Слобода Станица Хутор Ящик Аллея Балка \n"
					+ "Берег Бульвар Бугор Бухта Вал Въезд Горка Кооператив Дорога Канал Километр Кольцо Коса Линия Местечко Маяк Местность Мост Мыс Некоммерче \n"
					+ "Набережная Парк Переулок Переезд Площадь Площадка Полустанок Починок Причал Проспект Проезд Просек Просека Проселок Проток Протока Проулок \n"
					+ "Ряды Гск Село Сквер Товарищество Спуск Строение Тоннель Тракт Тупик Улица Участок Ферма Шоссе Эстакада Дом Партнерство Хозяйство Заезд";
			List<String> removeListTypes = new ArrayList<String>(Arrays.asList(addressTypes.split("\\s+")));

			//System.out.println("HERE1");
			//System.out.println("size"+removeListTypes.size());
			for (int i = 0; i < myList.size(); i++) {
			//	System.out.println("Bam");
				outerloop:
				for (int j = 0; j < removeListTypes.size(); j++) {
					//System.out.println("Bam2");
					if (myList.get(i).equalsIgnoreCase(removeListTypes.get(j))) {
						myList.remove(i);			
						break outerloop;
					}
				}
			}
			//System.out.println("HERE2");
			int myListSize = myList.size();

			if(myListSize > 1) {
				for (int i = 1; i < myListSize; i++) {
					String save1 = myList.get(i - 1);
					String save2 = myList.get(i);
	
					boolean addressWithPunctual = addressService.existsFormalName(save1 + "-" + save2);
					if (addressWithPunctual) {
						save1 = save1 + "-" + save2;
						myList.remove(i);
						myList.remove(i - 1);
						myList.add(i - 1, save1);
						i++;
						myListSize--;
					} else {
					}
				}
			}

			System.out.println(myList);

			if (myList.size() == 1) {
				Address first = addressService.getAddressFormalName(myList.get(0));
				System.out.println(first);
				if (first == null) {
					ArrayList<MyError> myerrorlist = new ArrayList<MyError>();
					MyError noInDb = new MyError();
					noInDb.setName("Информация:");
					noInDb.setType("К сожалению данного наименования нет в нашей базе данных");
					myerrorlist.add(noInDb);
					System.out.println(myerrorlist);
					model.addAttribute("myerrors", myerrorlist);
					request.setAttribute("myerrors", myerrorlist);
					try {
						request.getRequestDispatcher("/WEB-INF/tiles/home.jsp").forward(request, response);
					} catch (ServletException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return "home";
				}
				if (first.getParentguid() != null) {
					List<Address> addressesForOne = addressService.getQueryAddress(myList.get(0));
					ArrayList<String> formalNameFull = new ArrayList<String>();
					ArrayList<String> formalType = new ArrayList<String>();

					for (int i = 0; i < 3; i++) {
						formalNameFull = new ArrayList<String>();
					}

					Map<Integer, String> kMap = new HashMap<Integer, String>();
					Map<Integer, String> kMap2 = new HashMap<Integer, String>();

					for (int i = 0; i < addressesForOne.size(); i++) {
						int k = 0;
						String firstForOne = addressesForOne.get(i).getParentguid();
						String formalNameFullString = addressesForOne.get(i).getFormalname();
						String formalTypeString = addressesForOne.get(i).getShortname();
						String formalPostalIndex = addressesForOne.get(i).getPostalcode();
						// System.out.println(current);
						formalNameFull.add(formalNameFullString);
						formalType.add(formalTypeString);

						while (firstForOne != null && firstForOne.length() > 0) {
							// first = addresses.get(i).getParentguid();
							Address address = addressService.getAddressGuid(firstForOne);
							firstForOne = address.getParentguid();
							String formalNameFullString2 = address.getFormalname();
							// System.out.println(formalNameFullString2);
							String formalTypeString2 = address.getShortname();
							// System.out.println(formalTypeString2);
							kMap.put(k, formalNameFullString2);
							kMap2.put(k, formalTypeString2);
							k++;
						}
						String formalNameFullSet = formalNameFullString + ".  " + formalTypeString + ", ";
						for (int z = 0; z < k; z++) {
							formalNameFull.add(kMap.get(z));
							formalType.add(kMap2.get(z));
							formalNameFullSet = formalNameFullSet + " " + kMap.get(z) + ", " + kMap2.get(z);
						}
						addressesForOne.get(i).setFormalname(formalNameFullSet);
						addresses.add(addressesForOne.get(i));
					}
				} else if (first.getAoguid() != null) {
					final List<Address> addressesForOne = addressService.getQueryAddress(value);
					final ArrayList<String> formalNameFull = new ArrayList<String>();
					final ArrayList<String> formalType = new ArrayList<String>();

					final Map<Integer, String> kMap = new HashMap<Integer, String>();
					final Map<Integer, String> kMap2 = new HashMap<Integer, String>();
					ExecutorService threadPool = Executors.newFixedThreadPool(addressesForOne.size());
					// submit jobs to be executing by the pool
					
					for (int i = 0; i < addressesForOne.size(); i++) {
						final Address current = addressesForOne.get(i);
						final String formalNameFullString = addressesForOne.get(i).getFormalname();
						final String formalTypeString = addressesForOne.get(i).getShortname();
						String formalPostalIndex = addressesForOne.get(i).getPostalcode();
						threadPool.submit(new Runnable() {
							public void run() {
								// some code to run in parallel
								int k = 0;
								// System.out.println(current);
								formalNameFull.add(formalNameFullString);
								formalType.add(formalTypeString);
								String firstForOne = current.getAoguid();
								while (firstForOne != null && firstForOne.length() > 0) {
									// first = addresses.get(i).getParentguid();
									Address address = addressService.getAddressParentGuid(firstForOne);
									firstForOne = address.getAoguid();
									String formalNameFullString2 = address.getFormalname();
									System.out.println(formalNameFullString2);
									String formalTypeString2 = address.getShortname();
									System.out.println(formalTypeString2);
									kMap.put(k, formalNameFullString2);
									kMap2.put(k, formalTypeString2);
									k++;
								}
								String formalNameFullSet = formalNameFullString + ".  " + formalTypeString + ", ";
								for (int z = 0; z < k; z++) {
									formalNameFull.add(kMap.get(z));
									// System.out.println("CHE"+kMap);
									// System.out.println("CHE2"+kMap2);
									formalType.add(kMap2.get(z));
									formalNameFullSet = formalNameFullSet + kMap.get(z) + ", " + kMap2.get(z);
									System.out.println("DA" + formalNameFullSet);
								}
								current.setFormalname(formalNameFullSet);
								addresses.add(current);
							}
						});
						
					}
					
					// once you've submitted your last job to the service it
					// should be shut down
					threadPool.shutdown();
					// wait for the threads to finish if necessary
					try {
						threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

		
				} else {
					System.out.println("No such address");
				}
			} else {
				for (int i = 1; i < myListSize; i++) {
					int base = 0;
					ArrayList<Address> saveAoGuId = new ArrayList<Address>();
					ArrayList<String> firstListAoGuId = new ArrayList<String>();
					ArrayList<Address> firstListAddresses = new ArrayList<Address>();
					ArrayList<String> secondListParentGuId = new ArrayList<String>();
					ArrayList<Address> secondListAddresses = new ArrayList<Address>();

					String save1 = myList.get(i - 1);
					String save2 = myList.get(i);

					List<Address> firstList;
					if (base == 0) {
						firstList = addressService.getAddressListByFormalName((save1)); // myList.get(i
																						// -
																						// 1)
					} else {
						firstList = saveAoGuId;
					}

					List<Address> secondList = addressService.getAddressListByFormalName(save2);
					List<Address> rememberList = secondList;

					for (int j = 0; j < firstList.size(); j++) {
						firstListAoGuId.add(firstList.get(j).getAoguid());
					}

					for (int j = 0; j < firstList.size(); j++) {
						firstListAddresses.add(firstList.get(j));
					}

					for (int k = 0; k < secondList.size(); k++) {
						secondListParentGuId.add(secondList.get(k).getParentguid());
					}

					for (int k = 0; k < secondList.size(); k++) {
						secondListAddresses.add(secondList.get(k));
					}

					int check = 0;

					ArrayList<String> rememberParentFormalNameAndType = new ArrayList<String>();
					String fullParentName = "";
					for (int j = 0; j < firstListAoGuId.size(); j++) {
						String firstListAoGuIdString = firstListAddresses.get(j).getAoguid();
						String firstListAoGuIdString333 = firstListAddresses.get(j).getAolevel();

						for (int k = 0; k < secondListParentGuId.size(); k++) {
							String secondListParentGuIdString = secondListAddresses.get(k).getParentguid();
							String secondListParentGuIdString333 = secondListAddresses.get(k).getAolevel();

							String secondListAoGuIdString = secondListAddresses.get(k).getAoguid();

							if (firstListAoGuIdString.equalsIgnoreCase(secondListParentGuIdString)) {
								saveAoGuId.add(secondListAddresses.get(k));
							
								base = 1;

								String topAo = firstListAddresses.get(j).getAolevel();
								String formalNameSecond = " " + secondListAddresses.get(k).getFormalname() + " ";
								String typeNameSecond = " " + secondListAddresses.get(k).getShortname() + " ";
								String formalNameFirst = " " + firstListAddresses.get(j).getFormalname() + " ";
								String typeNameFirst = " " + firstListAddresses.get(j).getShortname() + " ";

								// String formalNameFirst = " " +
								// secondListAddresses.get(k).getFormalname() +
								// " ";
								fullParentName = formalNameSecond + typeNameSecond + formalNameFirst + typeNameFirst;

								if (!topAo.equalsIgnoreCase("1")) {
									Address addressParent = addressService.getAddressGuid(firstListAoGuIdString);
									String firstForOne = addressParent.getParentguid();
									System.out.println(firstForOne);

									while (firstForOne != null) {
										addressParent = addressService.getAddressGuid(firstForOne);
										firstForOne = addressParent.getParentguid();
										System.out.println("Tyt" + addressParent.getFormalname().toString());
										String formalName = " " + addressParent.getFormalname().toString() + " ";
										String typeName = " " + addressParent.getShortname().toString() + " ";
										rememberParentFormalNameAndType.add(formalName);
										rememberParentFormalNameAndType.add(typeName);
									}
								}

								for (int z = 0; z < rememberParentFormalNameAndType.size(); z++) {
									fullParentName = fullParentName + rememberParentFormalNameAndType.get(z);
								}
								
								secondListAddresses.get(k).setFormalname(fullParentName);
								addresses.add(secondListAddresses.get(k));
								
								if (i == myList.size() - 1) {
									String additionalSearch = secondListAddresses.get(k).getAoguid();
									if (additionalSearch != null) {
										List<Address> ListAoGuIdCurrent = addressService
												.getAddressListByAoGuId(additionalSearch);
										for (int x = 0; x < ListAoGuIdCurrent.size(); x++) {

											ListAoGuIdCurrent.get(x).setFormalname(
													ListAoGuIdCurrent.get(x).getFormalname() + fullParentName);
										}
										addresses.addAll(ListAoGuIdCurrent);
									}
								}
								check = 1;
							}
						}
					}
				}
			}

			int iternumber = 0;
			if (addresses.size() > 10) {
				iternumber = 10;
			} else {
				iternumber = addresses.size();
			}

			for (int z = 0; z < iternumber; z++) {
				restrictedAddressesByTen.add(addresses.get(z));
			}

			if (restrictedAddressesByTen.size() == 0) {
				ArrayList<MyError> myerrorlist = new ArrayList<MyError>();
				MyError noInDb = new MyError();
				noInDb.setName("Информация:");
				noInDb.setType("К сожалению данного наименования нет в нашей базе данных");
				myerrorlist.add(noInDb);
				System.out.println(myerrorlist);
				model.addAttribute("myerrors", myerrorlist);
				request.setAttribute("myerrors", myerrorlist);
			} else {
				model.addAttribute("addresses", restrictedAddressesByTen); // addresses
				request.setAttribute("addresses", restrictedAddressesByTen);
			}
			try {
				request.getRequestDispatcher("/WEB-INF/tiles/home.jsp").forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println("exact Search");
			Address exactAddress = addressService.getExactResult(_formalnameexact, _code, _postalcode, _okato, _oktmo,
					_regioncode);
			System.out.println(exactAddress);
			addresses.add(exactAddress);
			model.addAttribute("addresses", addresses);
			request.setAttribute("addresses", addresses);
			try {
				request.getRequestDispatcher("/WEB-INF/tiles/home.jsp").forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return "home";
	}

	@RequestMapping("/loggedout")
	public String showLoggedOut() {
		return "loggedout";
	}

	@RequestMapping("/newaccount")
	public String showNewAccount(Model model) {

		model.addAttribute("user", new User());
		return "newaccount";
	}

	@RequestMapping(value = "/createaccount", method = RequestMethod.POST)
	public String createAccount(@Validated(FormValidationGroup.class) User user, BindingResult result) {

		if (result.hasErrors()) {
			return "newaccount";
		}

		user.setAuthority("ROLE_USER");
		user.setEnabled(1);

		if (usersService.exists(user.getUsername())) {
			result.rejectValue("username", "DuplicateKey.user.username");
			return "newaccount";
		}

		try {
			usersService.create(user);
		} catch (DuplicateKeyException e) {
			result.rejectValue("username", "DuplicateKey.user.username");
			return "newaccount";
		}

		return "accountcreated";
	}

	////////////////////////////// REFRESH/////////////////////////////////////////
	public void reportCurrentTime() {
		//System.out.println("mechanical" + mechanical);
		if (mechanical == 0) {
			System.out.println("checkupdate");
			fullLoadFinished = addressService.selectDatabaseFullUpdate();
			if (fullLoadFinished.equalsIgnoreCase("1")) {
				try {
					String httpAddress = addressService.getHttpAddress();
					String fileURL = httpAddress + "VerDate.txt";
					URL url = new URL(fileURL);
					Scanner s = new Scanner(url.openStream());
					verCheck = s.next().trim();
					s.close();
				} catch (IOException ex) {
					ex.printStackTrace(); // for now, simply output it.
				}

				String queryUpdateDate = addressService.databaseSelectUpdateDate();

				if (verCheck.equalsIgnoreCase(queryUpdateDate)) {
					System.out.println("Equal!");
				} else {
					System.out.println("New update - load is started!");
					adminLoadUpdate(null, null, null, null);
				}

				// System.out.println("The time is now " + dateFormat.format(new
				// Date()));
			} else if (fullLoadFinished.equalsIgnoreCase("0")) {
				adminLoadFullUpdate(null, null, null, null);
			}
		}

	}

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		System.out.println("checkConfig");

		Runnable mUpdateTimeTask = null;
		if (updateInt == 1) {
		}
		_taskRegistrar = taskRegistrar;

		_taskRegistrar.setScheduler(taskExecutor());
		_taskRegistrar.addTriggerTask(mUpdateTimeTask = new Runnable() {
			@Override
			public void run() {

				System.out.println("Run");
				reportCurrentTime();
			}
		}, new Trigger() {
			@Override
			public Date nextExecutionTime(TriggerContext triggerContext) {
				String newTime = addressService.getTime();
				int newTimeInt = Integer.parseInt(newTime) * 1000 * 60;
				Calendar nextExecutionTime = new GregorianCalendar();
				Date lastActualExecutionTime = triggerContext.lastActualExecutionTime();
				nextExecutionTime.setTime(lastActualExecutionTime != null ? lastActualExecutionTime : new Date());
				nextExecutionTime.add(Calendar.MILLISECOND, newTimeInt);
				return nextExecutionTime.getTime();
			}
		}

		);
	}

	class ThreadOne implements Runnable {
		private List<Address> addressesForOne;
		private int i;
		private Address current;

		public ThreadOne(List<Address> _addressesForOne, int _taskint) {
			this.addressesForOne = _addressesForOne;
			this.i = _taskint;
		}

		public Address getCurrent() {
			return current;
		}

		@Override
		public void run() {
			ArrayList<String> formalNameFull = new ArrayList<String>();
			ArrayList<String> formalType = new ArrayList<String>();

			for (int i = 0; i < 3; i++) {
				formalNameFull = new ArrayList<String>();
			}

			Map<Integer, String> kMap = new HashMap<Integer, String>();
			Map<Integer, String> kMap2 = new HashMap<Integer, String>();

			// Thread
			int k = 0;
			String firstForOne = addressesForOne.get(i).getAoguid();
			String formalNameFullString = addressesForOne.get(i).getFormalname();
			String formalTypeString = addressesForOne.get(i).getShortname();
			String formalPostalIndex = addressesForOne.get(i).getPostalcode();
			// System.out.println(current);
			formalNameFull.add(formalNameFullString);
			formalType.add(formalTypeString);

			while (firstForOne != null && firstForOne.length() > 0) {
				// first = addresses.get(i).getParentguid();
				Address address = addressService.getAddressParentGuid(firstForOne);
				firstForOne = address.getAoguid();
				String formalNameFullString2 = address.getFormalname();
				System.out.println(formalNameFullString2);
				String formalTypeString2 = address.getShortname();
				System.out.println(formalTypeString2);
				kMap.put(k, formalNameFullString2);
				kMap2.put(k, formalTypeString2);
				k++;
			}
			String formalNameFullSet = formalNameFullString + ".  " + formalTypeString + ", ";
			for (int z = 0; z < k; z++) {
				formalNameFull.add(kMap.get(z));
				// System.out.println("CHE"+kMap);
				// System.out.println("CHE2"+kMap2);
				formalType.add(kMap2.get(z));
				formalNameFullSet = formalNameFullSet + kMap.get(z) + ", " + kMap2.get(z);
				System.out.println("DA" + formalNameFullSet);
			}
			addressesForOne.get(i).setFormalname(formalNameFullSet);
			current = addressesForOne.get(i);
		}
	}

	class SAXHandler extends DefaultHandler {
		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes)
				throws SAXException {

			int attributeLength = attributes.getLength();
			if ("Object".equalsIgnoreCase(qName)) {
				Address address = new Address();
				for (int i = 0; i < attributeLength; i++) {

					String attrName = attributes.getQName(i);
					String attrVal = attributes.getValue(i);

					if (attrName.equals("AOID")) {
						if (attrVal != null) {
							address.setAoid(attrVal);
						} else {
							address.setAoid("NoId");
						}
					}
					if (attrName.equals("AOGUID")) {
						address.setAoguid(attrVal);
					}
					if (attrName.equals("PARENTGUID")) {
						address.setParentguid(attrVal);
					}
					if (attrName.equals("FORMALNAME")) {
						address.setFormalname(attrVal);
					}
					if (attrName.equals("SHORTNAME")) {
						address.setShortname(attrVal);
					}
					if (attrName.equals("AOLEVEL")) {
						address.setAolevel(attrVal);
					}
					if (attrName.equals("CODE")) {
						address.setCode(attrVal);
					}
					if (attrName.equals("ACTSTATUS")) {
						address.setActstatus(attrVal);
					}
					if (attrName.equals("OKATO")) {
						address.setOkato(attrVal);
					}
					if (attrName.equals("OKTMO")) {
						address.setOktmo(attrVal);
					}
					if (attrName.equals("POSTALCODE")) {
						address.setPostalcode(attrVal);
					}
					if (attrName.equals("REGIONCODE")) {
						address.setRegioncode(attrVal);
					}
				}

				if (logicForFull == 1) { // No check for first load - faster
					addressService.create(address); // push to Database
				} else {
					if (addressService.exists(address.getAoid())) {
						// Nothing
					} else {
						addressService.create(address);
					}
				}
			}
		}
	}
}
