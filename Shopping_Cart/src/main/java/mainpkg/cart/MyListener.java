package mainpkg.cart;

import java.io.FileNotFoundException;

public interface MyListener {
    public void onClick(Item item) throws FileNotFoundException;
}
