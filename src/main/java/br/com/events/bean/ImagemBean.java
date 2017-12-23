package br.com.events.bean;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 * @author Mateus Henrique Tofanello
 * @version 1.0
 * @since 1.0
 */

@SuppressWarnings("serial")
@ManagedBean
public class ImagemBean extends ImagemServlet {

	HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	String evento = (String) sessao.getAttribute("EVENTO_SESSAO");

	private List<String> images;

	public List<String> getImages() {
		return images;
	}

	public String getEvento() {
		return evento;
	}

	@PostConstruct
	public void init() {
		try {
			File pathDir = new File(System.getProperty("user.home") + File.separatorChar + "eclipse"
					+ File.separatorChar + "images" + File.separatorChar + evento + File.separatorChar);

			if (!pathDir.exists())
				return;

			File[] arquivos = pathDir.listFiles(new FileFilter() {
				public boolean accept(File jpg) {
					return (jpg.getName().endsWith(".jpg"));
				}
			});

			images = new ArrayList<String>();

			if (arquivos != null) {
				for (File arquivo : arquivos)
					images.add(arquivo.getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}