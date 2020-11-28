import java.util.Scanner;

class Vertice {
	private String nombre;
    private int numVertice;
    
    public Vertice() {}
    public Vertice(String x){
    	nombre = x;
	    numVertice = -1;}
    
    public String getNombre() {
		return nombre;}
	public void setNombre(String nombre) {
		this.nombre = nombre;}
	public int getNumVertice() {
		return numVertice;}
	public void setNumVertice(int numVertice) {
		this.numVertice = numVertice;}
    public String nomVertice() {
    	return nombre;}
    public boolean equals(Vertice n) {
    	return nombre.equals(n.nombre);}
    public void asigVert(int n){
        numVertice = n;}
    public String toString(){
    	return nombre + " (" + numVertice + ")";}     
}

class GrafoMatriz{
    int numVerts;
    static int maxVerts;
    Vertice [] verts;
   
    
    
    int [][] adya;
   
    public GrafoMatriz(){
    	this(maxVerts);
    }
    public GrafoMatriz(int max){
    	maxVerts=max;
        adya = new int [max][max];
        verts = new Vertice[max];
        for (int i = 0; i < max; i++)
        	for (int j = 0; i < max; i++)
        		adya[i][j] = 0;
        numVerts = 0;
    }
    
    public int numVertice(String vs) {
         Vertice v = new Vertice(vs);
         boolean e = false;
         int i = 0;
         for (; (i < numVerts) && !e;){
        	 e = verts[i].equals(v);
        	 if (!e) 
        		 i++ ;
        	 }
         return (i < numVerts) ? i : -1 ;
    }
    public void nuevoVertice (String nombre){
    	boolean esta = numVertice(nombre) >= 0;
    	if (!esta){
    		Vertice v = new Vertice(nombre);
    		v.asigVert(numVerts);
    		verts[numVerts++] = v;
    	}
    }
    public void nuevoArco(String ori, String des)throws Exception{
    	int vorigen, vdestino;
        vorigen = numVertice(ori);
        vdestino = numVertice(des);
        if (vorigen < 0 || vdestino < 0) throw new Exception ("El vertice no existe");
        adya[vorigen][vdestino] = 1;
    }
    public boolean adyacente(String ori, String des)throws Exception{
    	int vorigen, vdestino;
        vorigen = numVertice(ori);
        vdestino = numVertice(des);
        if (vorigen < 0 || vdestino < 0) throw new Exception ("El vertice no existe");
        return adya[vorigen][vdestino] == 1;
    }
    public static int[]recorrerAnchura(GrafoMatriz g, String org) throws Exception{
    	int w, v;
        int [] m;
     
        v = g.numVertice(org);
        
        int c =-1;
        if (v < 0) throw new Exception("Vertice origen no existe");
        
        ColaLista cola = new ColaLista();
        m = new int[g.numVerts];
        // los vertices se inician desmarcador
        for (int i = 0; i < g.numVerts; i++)
        m[i] = c;
        m[v] = 0; // se marca el vertice de origen
        cola.insertar(new Integer(v));
        while (! cola.colaVacia()){
        	Integer cw;
        	cw = (Integer) cola.quitar();
        	w = cw.intValue();
        	System.out.println("El Vetice " + g.verts[w] + " fue visitado");
        	// inserta en la cola los adyacentes de w no marcados
        	for (int u = 0; u < g.numVerts; u++)
        	if ((g.adya[w][u] == 1) && (m[u] == c))
        	{
        	// el vertice se marca con con el numero de arcos
        	m[u] = m[w] + 1;
        	cola.insertar(new Integer(u));
        	}
        }
        return m;
        }
    
}

class Nodo {
	Object elemento;
	Nodo siguiente;
	int dato;
	
	public Nodo(Object x){
		elemento = x;
		siguiente = null;
		}
	public Nodo(int x){
		dato = x;
	    siguiente = null;
	}
	public Nodo(int x, Nodo n){
	    dato = x;
	    siguiente = n;
	}
	
	public int getDato(){
	    return dato;
	}
	public Nodo getEnlace(){
	    return siguiente;
	}
	public void setEnlace(Nodo enlace){
	    this.siguiente = enlace;
	}
}//Class Nodo

class ColaLista { 
	protected Nodo frente;
	protected Nodo fin;
	
	public ColaLista(){
		frente = fin = null;
	}
	
    public void insertar(Object elemento){
    	Nodo a;
        a = new Nodo(elemento);
        if (colaVacia()){
        	frente = a;
        	}else{
        		fin.siguiente = a;
        	}
        fin = a;
        }
    public Object quitar() throws Exception{
    	Object aux;
    	if (!colaVacia()){
    		aux = frente.elemento;
    		frente = frente.siguiente;
    	}else
    		throw new Exception("Eliminar de una cola");
    	return aux;
    }
    public void borrarCola(){
    	for (; frente != null;){
    		frente = frente.siguiente;
        }
    	System.gc();
    }
    public Object frenteCola() throws Exception{
    	if (colaVacia()){
    		throw new Exception("La cola esta vacia");
        }
    	return (frente.elemento);
    }
    public boolean colaVacia(){
    	return (frente == null);
    	}
}//class ColaLista

class Arco{
	int destino;
    double peso;
    
    public Arco(int d){
    	destino = d;
    }
    public Arco(int d, double p){
    	this(d);
    	peso = p;
    	}
    
    public int getDestino(){
        return destino;
    }
    public boolean equals(Object n){
    	Arco a = (Arco)n;
    	return destino == a.destino;
    }
} 

public class PruebaGrafos {
	public static void main(String[] args) {
		Scanner entrada = new Scanner (System.in);
		
		System.out.println("Ingresa un maximo de vertices");
		int maximo =entrada.nextInt();
		GrafoMatriz grafo = new GrafoMatriz(maximo);
		
	    int opcion=0;
	    int x = 0;
	    do {
		System.out.println("============= Menu =============");
		System.out.println("Digite 1 para Añadir un vertice");
		System.out.println("Digite 2 para Añdir un arco");
		System.out.println("Digite 3 para Saber si son adyacentes");
		System.out.println("Digite 4 para Recorrer en anchura");
		System.out.println("Digite 5 para ***SALIR***");
	    opcion = entrada.nextInt();
	    entrada.nextLine();
	    switch(opcion) {
	    case 1: 
	    	if(x!=maximo) {
	    	System.out.println("Ingresa el nombre del vertice");
			String nombre=entrada.nextLine();
			grafo.nuevoVertice(nombre);
			System.out.println("Se añdio el vertice " + '"'+nombre+'"');
			x = x +1;
			break;
	    	}else {
	    		System.out.println("No se puede añadir otro vertice!");
	    		break;
	    	}
	    case 2:
	    	System.out.println("Ingresa el nombre del vertice de origen");
			String origen=entrada.nextLine();
			System.out.println("Ingresa el nombre del vertice  de destino");
			String destino=entrada.nextLine();
			try {
				grafo.nuevoArco(origen, destino);
				System.out.println("Se añdio el arco");
			} catch (Exception e) {
				System.out.println("Error no se ah podido añdio el arco! Los vertices deben de existir");
			}
			break;
	    case 3: 
	    	System.out.println("Ingresa el nombre del primer vertice");
			origen=entrada.nextLine();
			System.out.println("Ingresa el nombre del segundo vertice");
			destino=entrada.nextLine();
			try {
				System.out.println(grafo.adyacente(origen, destino)?"Son adyacentes":"No son adyacentes!");
			} catch (Exception e) {
				System.out.println("Ingresa vertices existentes!!");
			}
			break;
	    case 4: 
	    	System.out.println("Ingresa el nombre del vertice origen a recorrer");
			String or=entrada.nextLine();
			try {
				grafo.recorrerAnchura(grafo, or);
				System.out.println();
			} catch (Exception e) {
				System.out.println("Ingresa vertices existentes!!");
			}
			break;
	    case 5: 
	    	break;
	    default:
	    	System.out.println("Opcion incorrecta!!");
	    }
	    }while(opcion!=5);
	    
	}

}
