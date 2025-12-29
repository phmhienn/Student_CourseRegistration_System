/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author HUY
 */
public class ComboItem {
    private String id;
    private String name;

    public ComboItem(String id, String name) {
        this.id = id; this.name = name;
    }
    public String getId() { return id; }
    public String getName() { return name; }

    @Override
    public String toString() { return name; } // hiển thị trên combobox
}
