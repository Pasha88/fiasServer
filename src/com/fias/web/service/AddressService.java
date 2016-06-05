package com.fias.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fias.web.dao.Address;
import com.fias.web.dao.AddressDao;
import com.fias.web.dao.DatabaseConfig;
import com.fias.web.dao.User;
import com.fias.web.dao.UsersDao;

@Service("addressService")
public class AddressService {

	@Autowired
	private AddressDao addressDao;

	public void create(Address address) {
		addressDao.create(address);
	}

	public boolean exists(String aoid) {
		return addressDao.exists(aoid);
	}
	
	public boolean existsFormalName(String formalName) {
		return addressDao.existsFormalName(formalName);
	}

	public List<Address> getAddressList() {
		return addressDao.getAddressList();
	}

	public List<Address> getQueryAddress(String address) {
		return addressDao.getQueryAddresss(address);
	}

	public Address getAddress(String aoid) {
		return addressDao.getAddress(aoid);
	}

	public Address getAddressGuid(String aoguid) {
		return addressDao.getAddressGuid(aoguid);
	}

	public Address getAddressParentGuid(String parentGuid) {
		return addressDao.getAddressParentGuid(parentGuid);
	}

	public Address getAddressFormalName(String formalName) {
		return addressDao.getAddressFormalName(formalName);
	}

	public Address getAddressFormalNameAndParendId(String formalName, String parentId) {
		return addressDao.getAddressFormalNameAndParendId(formalName, parentId);
	}

	public Address getAddressFormalNameAoguid(String formalName, String aoguId) {
		// TODO Auto-generated method stub
		return addressDao.getAddressFormalNameAndAoguId(formalName, aoguId);
	}

	public List<Address> getQueryAoguidAddress(String address) {
		return addressDao.getQueryAoguidAddresss(address);
	}
	//

	public List<Address> getAddressListByFormalName(String addressFormalName) {
		return addressDao.getAddressListByFormalName(addressFormalName);
	}

	public List<Address> getAddressListByFormalNameAndParentGuId(String addressFormalName, String parentGuId) {
		return addressDao.getAddressListByFormalNameAndParentGuId(addressFormalName, parentGuId);
	}

	public Address getAddressListByFormalNameAndParentGuIdUnique(String addressFormalName, String parentGuId) {
		return addressDao.getAddressListByFormalNameAndParentGuIdUnique(addressFormalName, parentGuId);
	}

	public List<Address> getAddressListByAoGuId(String additionalSearch) {
		// TODO Auto-generated method stub
		return addressDao.getAddressListByAoGuId(additionalSearch);
	}

	public List<Address> getAddressListByAoGuIdFull(String firstListAoGuIdString) {
		// TODO Auto-generated method stub
		return addressDao.getAddressListByAoGuIdFull(firstListAoGuIdString);
	}

	public List<Address> getAddressListByParentIdFull(String secondListParentGuIdString) {
		// TODO Auto-generated method stub
		return addressDao.getAddressListByParentIdFull(secondListParentGuIdString);
	}

	public Address getAddressParentGuIdAndAoGuId(String firstListAoGuIdString, String secondListParentGuIdString) {
		// TODO Auto-generated method stub
		return addressDao.getAddressParentGuIdAndAoGuId(firstListAoGuIdString, secondListParentGuIdString);
	}

	public void databaseFullUpdateSetOne() {
		addressDao.databaseFullUpdate();
	}
	
	public void databaseFullUpdateSetZero() {
		addressDao.databaseFullUpdateToZero();
	}
	
	public void databaseAddressDrop() {
		addressDao.deleteFromAddress();
	}

	// check for date of last delta update
	public String databaseSelectUpdateDate() {
		return addressDao.selectUpdateDate();
	}

	// check for full update status
	public String selectDatabaseFullUpdate() {
		return addressDao.selectDatabaseFullUpdate();
	}

	// update Version of data load
	public void databaseSetDateUpdate(String verCheck) {
		// TODO Auto-generated method stub
		addressDao.databaseSetDateUpdate(verCheck);
	}
	
	//Update http address
	public void databaseSetHttpAddress(String httpAddress) {
		// TODO Auto-generated method stub
		addressDao.databaseSetHttpAddress(httpAddress);
	}
	
	//Update time to update
	public void databaseSetUpdateTime(String setUpdateTime) {
		// TODO Auto-generated method stub
		addressDao.databaseSetUpdateTime(setUpdateTime);
	}
	
    //get Exact expand result
	public Address getExactResult(String formalNameExact, String code, 
			String postalcode, String okato, String oktmo, String regioncode) {
		return addressDao.getExactResult(formalNameExact, code, 
				postalcode, okato, oktmo, regioncode);
	}
	
	public List<DatabaseConfig> getCurrentConfig() {
		return addressDao.getCurrentConfig();
	}

	public String getTime() {
		// TODO Auto-generated method stub
		return addressDao.selectTimeUpdate();
	}
	
	public String getHttpAddress() {
		// TODO Auto-generated method stub
		return addressDao.selectHttpAddress();
	}

	public void cancelAllQueries() {
		// TODO Auto-generated method stub
		addressDao.stopAllQueries();
	}
	
	public void updateStatusSet(String status) {
		// TODO Auto-generated method stub
		addressDao.updateStatusSet(status);
	}
	
	public String updateStatusGet() {
		// TODO Auto-generated method stub
		return addressDao.updateStatusGet();
	}



}
