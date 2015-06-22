package librerias;

public interface IVector<U extends Object> {
	
	void incluir(U u);
	void borrar();
	U obtener(int pos);

}


