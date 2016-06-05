package com.fias.web.dao;

import java.sql.ResultSet;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateSystemException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
@Component("addressDao")
public class AddressDao {

	@Autowired
	private SessionFactory sessionFactory;

	private Session session() {
		return sessionFactory.getCurrentSession();
	}

	@Transactional
	public void create(Address address) {
		session().save(address);
	}

	public boolean exists(String aoid) {
		Address address = getAddress(aoid);
		return address != null;
	}

	public List<Address> getAddressList() {
		return session().createQuery("from Address").list();
	}

	public List<Address> getQueryAddresss(String address) {
		Criteria crit = session().createCriteria(Address.class);
		// crit.add(Restrictions.idEq(address));
		// crit.add(Restrictions.)
		// crit.add(Restrictions.like("formalname",address));
		// Restrictions.like("stateName", "Virg%");
		// crit.add(Restrictions.eq("formalname", "%"+address+"%"));
		crit.add(Restrictions.like("formalname", address, MatchMode.ANYWHERE));
		crit.setMaxResults(10);
		return crit.list();
	}

	public Address getAddress(String aoid) {
		Criteria crit = session().createCriteria(Address.class);
		crit.add(Restrictions.idEq(aoid));
		return (Address) crit.uniqueResult();
	}
	
	public Address getAddressFormalNameExact(String formalNameExact) {
		Criteria crit = session().createCriteria(Address.class);
		crit.add(Restrictions.eq("formalname", formalNameExact));
		return (Address) crit.uniqueResult();
	}

	public Address getAddressGuid(String aoguid) {
		Criteria crit = session().createCriteria(Address.class);
		crit.add(Restrictions.eq("aoguid", aoguid));
		crit.setMaxResults(1);
		return (Address) crit.uniqueResult();
	}

	public Address getAddressParentGuid(String parentguid) {
		Criteria crit = session().createCriteria(Address.class);
		crit.add(Restrictions.eq("parentguid", parentguid));
		crit.setMaxResults(1);
		return (Address) crit.uniqueResult();
	}

	public Address getAddressFormalName(String formalName) {
		// TODO Auto-generated method stub
		Criteria crit = session().createCriteria(Address.class);
		// crit.add(Restrictions.eq("formalname", formalName));
		crit.add(Restrictions.like("formalname", formalName, MatchMode.ANYWHERE));
		crit.setMaxResults(1);
		return (Address) crit.uniqueResult();
	}

	public Address getAddressFormalNameAndParendId(String formalName, String parentId) {
		// TODO Auto-generated method stub
		Criteria crit = session().createCriteria(Address.class);
		crit.add(Restrictions.eq("formalname", formalName));
		crit.add(Restrictions.eq("parentguid", parentId));
		crit.setMaxResults(1);
		return (Address) crit.uniqueResult();
	}

	public Address getAddressFormalNameAndAoguId(String formalName, String parentId) {
		// TODO Auto-generated method stub
		Criteria crit = session().createCriteria(Address.class);
		crit.add(Restrictions.eq("formalname", formalName));
		crit.add(Restrictions.eq("aoguid", parentId));
		crit.setMaxResults(1);
		return (Address) crit.uniqueResult();
	}

	public Address getAddressParentGuIdAndAoGuId(String parentGuId, String AoGuId) {
		// TODO Auto-generated method stub
		Criteria crit = session().createCriteria(Address.class);
		crit.add(Restrictions.eq("parentguid", AoGuId));
		crit.add(Restrictions.eq("aoguid", parentGuId));
		crit.setMaxResults(1);
		return (Address) crit.uniqueResult();
	}

	public List<Address> getQueryAoguidAddresss(String aoguid) {
		// TODO Auto-generated method stub
		Criteria crit = session().createCriteria(Address.class);
		crit.add(Restrictions.eq("parentguid", aoguid));
		crit.setMaxResults(10);
		return crit.list();
	}

	public List<Address> getAddressListByFormalName(String addressFormalName) {
		Criteria crit = session().createCriteria(Address.class);
		crit.add(Restrictions.like("formalname", addressFormalName, MatchMode.ANYWHERE));
		// crit.add(Restrictions.eq("formalname", addressFormalName));
		return crit.list();
	}

	public List<Address> getAddressListByFormalNameAndParentGuId(String addressFormalName, String parentGuId) {
		// TODO Auto-generated method stub
		Criteria crit = session().createCriteria(Address.class);
		crit.add(Restrictions.like("formalname", addressFormalName, MatchMode.ANYWHERE));
		crit.add(Restrictions.like("parentguid", parentGuId, MatchMode.ANYWHERE));
		return crit.list();
	}

	public Address getAddressListByFormalNameAndParentGuIdUnique(String addressFormalName, String parentGuId) {
		// TODO Auto-generated method stub
		Criteria crit = session().createCriteria(Address.class);
		crit.add(Restrictions.eq("formalname", addressFormalName));
		crit.add(Restrictions.eq("parentguid", parentGuId));
		crit.setMaxResults(1);
		return (Address) crit.uniqueResult();
	}

	public List<Address> getAddressListByAoGuId(String aoguid) {
		// TODO Auto-generated method stub
		Criteria crit = session().createCriteria(Address.class);
		// Restrictions.like("stateName", "Virg%");
		// crit.add(Restrictions.eq("formalname", "%"+address+"%"));
		// crit.add(Restrictions.ne("parentguid", aoguid));
		crit.add(Restrictions.eq("parentguid", aoguid));
		crit.setMaxResults(10);
		return crit.list();
	}
	

	public List<Address> getAddressListByAoGuIdFull(String aoGuId) {
		Criteria crit = session().createCriteria(Address.class);
		crit.add(Restrictions.eq("parentguid", aoGuId));
		return crit.list();
	}

	public List<Address> getAddressListByParentIdFull(String parentGuId) {
		Criteria crit = session().createCriteria(Address.class);
		crit.add(Restrictions.eq("aoguid", parentGuId));
		crit.setMaxResults(1);
		return crit.list();
	}

	public void databaseFullUpdate() {
		String sql = "update databaseupdate set databasefullupdate=1 where id=1";
		SQLQuery query = session().createSQLQuery(sql);
		query.executeUpdate();
	}
	
	public void databaseFullUpdateToZero() {
		String sql = "update databaseupdate set databasefullupdate=0 where id=1";
		SQLQuery query = session().createSQLQuery(sql);
		query.executeUpdate();
	}
	
	public void deleteFromAddress() {
		String sql = "delete from address";
		SQLQuery query = session().createSQLQuery(sql);
		query.executeUpdate();
	}

	public void databaseSetDateUpdate(String dateUpdate) {
		String sql = "update databaseupdate set databasedeltaupdate='" + dateUpdate + "' where id=1";
		SQLQuery query = session().createSQLQuery(sql);
		query.executeUpdate();
	}

	public String selectUpdateDate() {
		String sql = "select databasedeltaupdate from databaseupdate where id=1";
		// SQLQuery query = session().createSQLQuery(sql);
		// List a = query.list();
		// //String my = query.getQueryString();
		// String updateDate = a.get(0).toString();
		// return updateDate;
		SQLQuery query = session().createSQLQuery(sql);
		String updateDate = query.uniqueResult().toString();
		System.out.println(updateDate);
		return updateDate;
	}
	
	public String selectTimeUpdate() {
		String sql = "select checkupdatetime from databaseupdate where id=1";
		// SQLQuery query = session().createSQLQuery(sql);
		// List a = query.list();
		// //String my = query.getQueryString();
		// String updateDate = a.get(0).toString();
		// return updateDate;
		SQLQuery query = session().createSQLQuery(sql);
		String updateTime = query.uniqueResult().toString();
		System.out.println(updateTime);
		return updateTime;
	}
	
	public String selectHttpAddress() {
		String sql = "select httpaddress from databaseupdate where id=1";
		SQLQuery query = session().createSQLQuery(sql);
		String httpaddress = query.uniqueResult().toString();
		System.out.println(httpaddress);
		return httpaddress;
	}

	public String selectDatabaseFullUpdate() {
		String sql = "select databasefullupdate from databaseupdate where id=1";
		SQLQuery query = session().createSQLQuery(sql);
		String isFullUpdateFinish = query.uniqueResult().toString();
		System.out.println(isFullUpdateFinish);
		return isFullUpdateFinish;
	}
	
	public void databaseSetHttpAddress(String httpAddress) {
		String sql = "update databaseupdate set httpaddress='" + httpAddress + "' where id=1";
		SQLQuery query = session().createSQLQuery(sql);
		query.executeUpdate();
	}
	
	public void databaseSetUpdateTime(String setUpdateTime) {
		String sql = "update databaseupdate set checkupdatetime='" + setUpdateTime + "' where id=1";
		SQLQuery query = session().createSQLQuery(sql);
		query.executeUpdate();
	}
	
	public Address getExactResult(String formalNameExact, String code, 
			String postalcode, String okato, String oktmo, String regioncode) {
		// TODO Auto-generated method stub
		Session hibernateSession = session(); 
		Criteria crit =  hibernateSession.createCriteria(Address.class);
  	    hibernateSession.cancelQuery();
		if(!formalNameExact.equals("")) 
			crit.add(Restrictions.eq("formalname", formalNameExact));
		if(!code.equals("")) 
			crit.add(Restrictions.eq("code", code));
		if(!postalcode.equals("")) 
			crit.add(Restrictions.eq("postalcode", postalcode));
		if(!okato.equals("")) 
			crit.add(Restrictions.eq("okato", okato));
		if(!oktmo.equals("")) 
			crit.add(Restrictions.eq("oktmo", oktmo));
		if(!regioncode.equals("")) 
			crit.add(Restrictions.eq("formalname", regioncode));
		
		crit.setMaxResults(1);
        if(hibernateSession.isConnected()) {
        	System.out.println("connected");
        }
    	return (Address) crit.uniqueResult();
	}

	public boolean existsFormalName(String formalName) {
		// TODO Auto-generated method stub
		Address address =  getAddressFormalNameExact(formalName);
		return address != null;
	}
	
	public List<DatabaseConfig> getCurrentConfig() {
		return session().createQuery("from DatabaseConfig").list();
	}

	public void stopAllQueries() {
		// TODO Auto-generated method stub
		session().cancelQuery();
		session().flush();
		session().close();
	}
	
	public void updateStatusSet(String updateStatus) {
		String sql = "update databaseupdate set updatestatus='" + updateStatus + "' where id=1";
		SQLQuery query = session().createSQLQuery(sql);
		query.executeUpdate();
	}
	
	public String updateStatusGet() {
		String sql = "select updatestatus from databaseupdate where id=1";
		SQLQuery query = session().createSQLQuery(sql);
		String updatestatus = query.uniqueResult().toString();
		System.out.println(updatestatus);
		return updatestatus;
	}

}
