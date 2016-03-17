package com.shavika.agritech.service;

import java.io.IOException;
import java.util.List;

import com.shavika.agritech.api.exception.AgriTechAppException;
import com.shavika.agritech.db.dto.ReservoirLevel;

public interface GathererService {

	public void openURL(String url) throws AgriTechAppException, IOException;

	public List<ReservoirLevel> getreservoirlevel() throws AgriTechAppException, IOException;

}
