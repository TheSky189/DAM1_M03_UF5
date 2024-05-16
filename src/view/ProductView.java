package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import main.Shop;
import model.Product;
import java.awt.Font;

public class ProductView extends JDialog implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JTextField productNameField;
    private JTextField stockField;
    private JTextField priceField;
    private Shop shop;
    private int option;

    public ProductView(Shop shop, int option) {
        this.shop = shop;
        this.option = option;
        setTitle(getDialogTitle(option)); // Establecer el titulo de la ventana segun la opcion
        setSize(507, 458);
        getContentPane().setLayout(null);

        // Crear etiquetas y campos de texto para ingresar datos del producto
        JLabel nameLabel = new JLabel("Nombre:");
        nameLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
        nameLabel.setBounds(153, 91, 80, 20);
        getContentPane().add(nameLabel);

        productNameField = new JTextField();
        productNameField.setBounds(153, 121, 210, 20);
        getContentPane().add(productNameField);

        JLabel stockLabel = new JLabel("Stock:");
        stockLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
        stockLabel.setBounds(153, 151, 80, 20);
        getContentPane().add(stockLabel);

        stockField = new JTextField();
        stockField.setBounds(153, 181, 210, 20);
        getContentPane().add(stockField);

        JLabel priceLabel = new JLabel("Precio público:");
        priceLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
        priceLabel.setBounds(153, 211, 100, 20);
        getContentPane().add(priceLabel);

        priceField = new JTextField();
        priceField.setBounds(153, 241, 210, 20);
        getContentPane().add(priceField);

        // Crear boton "OK" para confirmar la operacion
        JButton okButton = new JButton("OK");
        okButton.setBounds(153, 289, 100, 30);
        getContentPane().add(okButton);
        okButton.addActionListener(this); 
        
        JButton cancelButton = new JButton("Cancelar");
        cancelButton.setBounds(263, 289, 100, 30);
        getContentPane().add(cancelButton);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Obtener los datos ingresados por el usuario
        String productName = productNameField.getText();
        int stock = Integer.parseInt(stockField.getText());
        double price = Double.parseDouble(priceField.getText());

        // Realizar la operacion correspondiente segun la opcion seleccionada
        switch (option) {
            case 2:
                addProduct(productName, stock, price);
                break;
            case 3:
                addStock(productName, stock);
                break;
            case 9:
                removeProduct(productName);
                break;
            default:
                break;
        }
    }

    // Método para añadir un nuevo producto
    private void addProduct(String name, int stock, double price) {
        // Verificar si el producto ya existe
        if (shop.findProduct(name) != null) {
            JOptionPane.showMessageDialog(this, "El producto ya existe", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            // Agregar el nuevo producto al inventario
            shop.addProduct(new Product(name, price, true, stock));
            JOptionPane.showMessageDialog(this, "Producto añadido correctamente", "Informacion", JOptionPane.INFORMATION_MESSAGE);
        }
        dispose(); // cerrar ventana
    }

    // Añadir stock a un producto existente
    private void addStock(String name, int stock) {
        Product product = shop.findProduct(name);
        if (product != null) {
        	// Actualizar
            product.setStock(product.getStock() + stock);
            JOptionPane.showMessageDialog(this, "Stock añadido correctamente", "Informacion", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "El producto no existe", "Error", JOptionPane.ERROR_MESSAGE);
        }
        dispose();
    }

    // Eliminar un producto
    private void removeProduct(String name) {
        Product product = shop.findProduct(name);
        if (product != null) {
        	// Eliminar producto
            shop.removeProduct();
            JOptionPane.showMessageDialog(this, "Producto eliminado correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "El producto no existe", "Error", JOptionPane.ERROR_MESSAGE);
        }
        dispose();
    }

    // Obtener el titulo de la ventana segun la opcion seleccionada
    private String getDialogTitle(int option) {
        switch (option) {
            case 2:
                return "Añadir producto";
            case 3:
                return "Añadir stock";
            case 9:
                return "Eliminar producto";
            default:
                return "";
        }
    }
}
