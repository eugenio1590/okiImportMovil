package conexion;

import java.util.Vector;

public interface IConexionDAO {
	
	public class StringUtil{
		
		 public static final String[] concat(String[] s1, String[] s2) {
		      String[] erg = new String[s1.length + s2.length];

		      System.arraycopy(s1, 0, erg, 0, s1.length);
		      System.arraycopy(s2, 0, erg, s1.length, s2.length);

		      return erg;
		  }
	}
	
	public class ObjetosCombo<T>{
		T id;
		String nombre;
		
		//Constructor
		public ObjetosCombo(T id, String nombre) {
			super();
			this.id = id;
			this.nombre = nombre;
		}
		
		@Override
		public String toString() {
			return nombre;
		}
		
		public T getId() {
			return id;
		}
	}
	
	//Metodos
	public abstract void consultar(boolean distinct, String table, String[] columns, String criterio,
                                   String[] valores_criterio, String groupBy, String having,
                                   String orderBy, String limit);
	
	public abstract <X> Vector<X> extraerValor(int position);
	
	public abstract String extraerValor(String columna, int position);

	//<X> X insertar(X x, ConexionDAO conex);
	
	//public abstract void insertar(Class<?> X, ConexionDAO conex);
	
	//Por ahora
		//Insertar
		//Modificar
		//Eliminar
	
}
