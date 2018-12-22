package stage2;

import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;
import common.api.*;

@RestController
public class StatController
{
	@Autowired
	StatManager statManager;

	@PostMapping(path = "/file", consumes = MediaType.APPLICATION_JSON_VALUE)
	void receiveFileJson(@RequestBody FileInfo fi) {
		statManager.acceptFile(fi);
	}    

	@PostMapping(path = "/file", consumes = MediaType.APPLICATION_XML_VALUE)
	void receiveFileXml(@RequestBody FileInfo fi) {
		statManager.acceptFile(fi);
	}    

	@GetMapping(path = "/stat", produces = MediaType.APPLICATION_JSON_VALUE)
	Statistics getStatistics() {
		return statManager.getStatistics();
	}
}
