package Controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.xml.ws.runtime.dev.Session;

import dao.DaoPartido;
import dao.DaoPorra;
import dao.DaoUsuario;
import dao.DaoJornada;
import dao.DaoPartido;
import dao.DaoUsuario;
import model.Apuesta;
import model.Jornada;
import model.Partido;
import model.Porra;
import model.PorraPK;
import model.Usuario;


/**
 * Servlet implementation class Controller
 */
@WebServlet("/Controller")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Controller() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		List<Jornada> jornadas = null;
		DaoJornada daoJornada = new DaoJornada();
		String msg=null;
		DaoPartido daoPartido = new DaoPartido();
		String idPartido = null;
		DaoPorra daoPorra =new DaoPorra();


		//La sesión se utiliza para almacenar información específica del usuario durante su interacción con la aplicación web.
		HttpSession session = request.getSession();
		String op = request.getParameter("op");

		switch(op) {

		case "inicio":

			try {
				jornadas= daoJornada.listadoJornadas();

			}catch (Exception e) {
				e.printStackTrace();
			}

			session.setAttribute("jornadas", jornadas);
			request.getRequestDispatcher("home.jsp").forward(request, response);
			break;

		case "vajornada":

			List<Partido> listaPartidos = null;

			long idJornada = Long.parseLong(request.getParameter("jornada"));

			try {
				listaPartidos = daoPartido.getPartidoPorJornada(idJornada);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			session.setAttribute("listaPartidos", listaPartidos);
			request.getRequestDispatcher("home.jsp").forward(request, response);
			break;

		case "login":

			DaoUsuario daoUsuario = new DaoUsuario();
			Usuario usuario = null;

			String dni=request.getParameter("dni");
			String nombre=request.getParameter("nombre");

			try {
				usuario = daoUsuario.buscarUsuario(dni);
				msg="login realizado correctamente";
				if(usuario == null) {
					usuario = new Usuario(dni);
					usuario.setNombre(nombre);
					daoUsuario.addUsuario(usuario);
					msg="Usuario creado conrrectamente";
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			session.setAttribute("msg", msg);
			session.setAttribute("usuario", usuario);
			request.getRequestDispatcher("home.jsp").forward(request, response);
			break;

		case "logout":

			session.removeAttribute("usuario");
			msg="Hasta pronto";
			request.getRequestDispatcher("home.jsp").forward(request, response);
			break;

		case "apuesta":
			
			Porra porra =null;
			PorraPK porraPK =null;
			Partido partido =null;

			String golesVisitante=request.getParameter("golesVisitante");
			String golesLocal=request.getParameter("golesLocal");
			idPartido =request.getParameter("partido");

			usuario =(Usuario)session.getAttribute("usuario");
			porraPK=new PorraPK();
			porraPK.setDni(usuario.getDni());
			porraPK.setIdpartido(Long.valueOf(idPartido));


			try {
				porra = daoPorra.buscarPorra(porraPK);

				if(porra == null) {
					porra = new Porra(porraPK, BigDecimal.valueOf(Long.valueOf(golesLocal)),BigDecimal.valueOf(Long.valueOf(golesVisitante)));
					porra.setUsuario(usuario);
					partido=daoPartido.buscarPartido(Long.valueOf(idPartido));
					porra.setPartido(partido);

					daoPorra.addPorra(porra);
					msg="Apuesta realizada correctamente";
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				msg="Error al intentar realizar apuesta";

			}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				msg="Error al intentar realizar apuesta";

			}

			session.setAttribute("msg", msg);
			request.getRequestDispatcher("home.jsp").forward(request, response);
			break;
			
		case "resumen":

			idPartido = request.getParameter("partido");
			ArrayList<Apuesta> apuestas = new ArrayList<Apuesta>();			

			try {
				apuestas = daoPorra.getResumenApuesta(Long.valueOf(idPartido));

			}catch (Exception e) {
				e.printStackTrace();
			}

			session.setAttribute("apuestas", apuestas);
			request.getRequestDispatcher("resumenApuestas.jsp").forward(request, response);
			break;

		}}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}