

import java.io.InputStream;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import com.minotauro.user.xml.prof.ObjectFactory;
import com.minotauro.user.xml.prof.ProfList;

/**
 * @author Demián Gutierrez
 */
public class ProfLoader {

  public List<MProf> load(InputStream is) throws Exception {
    JAXBContext jc = JAXBContext.newInstance(ObjectFactory.class.getPackage().getName());
    Unmarshaller unmarshaller = jc.createUnmarshaller();

    ProfList xmlProfList = (ProfList) unmarshaller.unmarshal(is);

    loadProfList(xmlProfList.getProf());

    return profList;
  }
}
