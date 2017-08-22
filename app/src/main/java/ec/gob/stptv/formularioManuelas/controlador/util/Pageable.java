package ec.gob.stptv.formularioManuelas.controlador.util;

import java.util.List;

/**
 * Created by lmorales on 19/07/17.
 */
public class Pageable<T>  {

    //el tamaño de pagina predeterminado
    public static final int DEFAULT_PAGE_SIZE = 10;

    private static final int PAGE_WINDOW = 10;

    //La lista sobre la cual esta clase está paginando
    private List<T> list;

    //El tamaño de pagina
    private int pageSize = DEFAULT_PAGE_SIZE;

    //la pagina actual
    private int page;

    //el indice inicial
    private int startingIndex;

    //el indice final
    private int endingIndex;

    //el número maximo de paginas
    private int maxPages;

    /**
     * Crea una nueva instancia con la lista especificada
     *
     * @param list    a List
     */
    public Pageable(List<T> list) {
        this.list = list;
        this.page = 1;
        this.maxPages = 1;

        calculatePages();
    }

    /**
     * Método que calcula el número de páginas
     */
    private void calculatePages() {
        if (pageSize > 0) {
            // calculate how many pages there are
            if (list.size() % pageSize == 0) {
                maxPages = list.size() / pageSize;
            } else {
                maxPages = (list.size() / pageSize) + 1;
            }
        }
    }

    /**
     * Obtiene la lista que está paginando.
     *
     * @return  a List
     */
    public List<T> getList() {
        return this.list;
    }

    /**
     * Obtiene el subconjunto de la lista para la página actual.
     *
     * @return  a List
     */
    public List<T> getListForPage() {
        return list.subList(startingIndex, endingIndex);
    }

    /**
     * obtiene el tamaño de página
     *
     * @return  the page size as an int
     */
    public int getPageSize() {
        return this.pageSize;
    }

    /**
     * Establece el tamaño de la página.
     *
     * @param pageSize   the page size as an int
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
        calculatePages();
    }

    /**
     * obtiene la página
     *
     * @return  the page as an int
     */
    public int getPage() {
        return this.page;
    }

    /**
     * Establece el tamaño de página
     *
     * @param p
     */
    public void setPage(int p) {
        if (p >= maxPages) {
            this.page = maxPages;
        } else if (p <= 1) {
            this.page = 1;
        } else {
            this.page = p;
        }

        startingIndex = pageSize * (page-1);
        if (startingIndex < 0) {
            startingIndex = 0;
        }
        endingIndex = startingIndex + pageSize;
        if (endingIndex > list.size()) {
            endingIndex = list.size();
        }
    }

    /**
     * Obtiene el número maximo de paginas
     * @return
     */
    public int getMaxPages() {
        return this.maxPages;
    }

    /**
     * Determina si hay una página anterior y obtiene el número de página.
     *
     * @return
     */
    public int getPreviousPage() {
        if (page > 1) {
            return page-1;
        } else {
            return 0;
        }
    }

    /**
     * Determina si hay una página siguiente y obtiene el número de página.
     *
     * @return
     */
    public int getNextPage() {
        if (page < maxPages) {
            return page+1;
        } else {
            return 0;
        }
    }

    /**
     * Obtiene la página mñinima en la ventana
     *
     * @return
     */
    public int getMinPageRange() {
        if (getPage() > PAGE_WINDOW) {
            return getPage() - PAGE_WINDOW;
        } else {
            return 1;
        }
    }

    /**
     * Obtiene la página máxima en la ventana.
     *
     * @return
     */
    public int getMaxPageRange() {
        if (getPage() < (getMaxPages() - PAGE_WINDOW)) {
            return getPage() + PAGE_WINDOW;
        } else {
            return getMaxPages();
        }
    }
}
