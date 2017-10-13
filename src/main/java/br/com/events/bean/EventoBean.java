package br.com.events.bean;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

/**
 * @author Mateus Henrique Tofanello
 * @version 1.0
 * @since 1.0
 */

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class EventoBean extends ImagemServlet implements Serializable {

	HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	String pathEvento = System.getProperty("user.home") + File.separatorChar + "Events" + File.separatorChar;

	private File[] eventos;

	private String eventosFiltered;

	public File[] getEventos() {
		return eventos;
	}

	public String getEventosFiltered() {
		return eventosFiltered;
	}

	public void setEventosFiltered(String eventosFiltered) {
		this.eventosFiltered = eventosFiltered;
	}

	@PostConstruct
	public void listarDir() {
		try {
			File folder = new File(pathEvento);
			File[] listOfFolders = folder.listFiles(File::isDirectory);

			Arrays.sort(listOfFolders, new Comparator<File>() {
				public int compare(File f1, File f2) {
					return Long.valueOf(f2.lastModified()).compareTo(f1.lastModified());
				}
			});
			eventos = listOfFolders;

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os diretórios");
			erro.printStackTrace();
		}
	}

	public void visualizarFotos(ActionEvent evento) throws IOException {
		try {
			sessao.setAttribute("EVENTO_SESSAO", evento.getComponent().getAttributes().get("eventoSelecionado"));
			Faces.redirect("./gallery/fotos.xhtml");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao carregar a tela de arquivos da obra");
			erro.printStackTrace();
		}
	}

	public void filter() {
		File folder = new File(pathEvento);
		File[] listOfFolders = folder.listFiles(new FileFilter() {
			public boolean accept(File filter) {
				if (filter.isDirectory())
					return filter.getName().toLowerCase().contains(getEventosFiltered().toLowerCase());
				return false;
			}
		});
		Arrays.sort(listOfFolders, new Comparator<File>() {
			public int compare(File f1, File f2) {
				return Long.valueOf(f2.lastModified()).compareTo(f1.lastModified());
			}
		});
		eventos = listOfFolders;
	}
}