package com.shavika.agritech.rs.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.shavika.agritech.api.db.DBConnection;
import com.shavika.agritech.api.db.SessionFactory;
import com.shavika.agritech.api.db.SessionFactoryImpl;
import com.shavika.agritech.api.exception.AgriTechAppException;
import com.shavika.agritech.db.dto.ReservoirLevel;
import com.shavika.agritech.service.GathererService;
import com.shavika.agritech.service.GathererServiceImpl;
import com.shavika.agritech.utils.DateTimeUtil;
import com.shavika.test.database.Employee;

@Path("/test")
public class Test {

	private static final Logger LOGGER = Logger.getLogger(Test.class);

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String respondAsReady() throws SQLException {
		LOGGER.info("Calling Default get method...");
		Connection connection = DBConnection.getConnection();
		LOGGER.info("Calling Default get method.../i am connection ..." + connection.getAutoCommit());

		//reservoirlevel();

		// Employee employee = createDummyObject();
		// SessionFactory<Employee> session = new SessionFactoryImpl<Employee>();
		// int count = session.save(employee);
		// System.out.println("Insert count ==>" + count);
		//
		// int count = session.update(employee);
		// System.out.println("Update count ==>" + count);

		// List<Employee> lists = session.findAll(Employee.class);
		// for (Employee emp : lists)
		// System.out.println(emp);
		//
		// Employee emp1 = (Employee) session.find(Employee.class, 32);
		// System.out.println("===>" + emp1);
		//
		// int count = session.deleteById(Employee.class, 31);
		// System.out.println("delete count ==>" + count);
		//
		// count = session.deleteAsSoft(Employee.class, 20);
		// System.out.println("soft delete count ==>" + count);

		return new String("Data filed...");
	}

	private static Employee createDummyObject() {

		Employee employee = new Employee();
		employee.setId(34);
		employee.setFirst_name("Gama");
		employee.setLast_name("Gamanna");
		employee.setMiddle_name("gam");
		employee.setEmployee_id("477477");
		employee.setIs_deleted(false);
		employee.setGender("M");
		employee.setCreated_on(System.currentTimeMillis());
		employee.setModified_on(System.currentTimeMillis());
		return employee;

	}

	private static void reservoirlevel() throws SQLException {
		String url = "http://dmc.kar.nic.in/RL.pdf";
		try {
			GathererService gs = new GathererServiceImpl();
			gs.openURL(url);
			List<ReservoirLevel> reservoirInfo = gs.getreservoirlevel();
			System.out.println("reservoirInfo==>" + reservoirInfo.size());
			
			SessionFactory<ReservoirLevel> session = new SessionFactoryImpl<ReservoirLevel>();
			int count = session.save(reservoirInfo);
			System.out.println("Insert count ==>" + count);

		} catch (AgriTechAppException | IOException e) {
			e.printStackTrace();
		}

	}

	private static void jodatime() {
		DateTime dt = new DateTime();
		DateTimeFormatter fmt = DateTimeFormat.forPattern("dd, MMMM, yyyy - z");
		String str = fmt.print(dt);
		System.out.println(str);
		System.out.println(dt.getMillis());
		System.out.println(DateTimeUtil.defaultDateTime());
		System.out.println(DateTimeUtil.dateWithFormat(DateTimeUtil.TIMEFORMAT_HH_CO_MM_CO_SS));
		System.out.println(DateTimeUtil.getMillis());
		System.out.println(DateTimeUtil.getDateTime(DateTimeUtil.getMillis()));

	}

	public static void main(String[] args) throws SQLException {
		// jodatime();
		// reservoirlevel();
		long l = 1457548200000L;
		System.out.println(DateTimeUtil.getDateTime(l));
	}

}