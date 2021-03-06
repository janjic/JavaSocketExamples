
package klijent.requesthandler;

import klijent.session.Sesija;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import klijent.responsehandler.AsyncResponseHandler;
import transfer.TOKlijentZahtev;

public class RequestHandler {
    
    Socket ksoket;
    ObjectOutputStream out;
    ObjectInputStream in;
    private boolean logavan;

    public void setLogavan(boolean logavan) {
        this.logavan = logavan;
    }

    
    
    public Socket getKsoket() {
        return ksoket;
    }

    public ObjectOutputStream getOut() {
        return out;
    }

    public ObjectInputStream getIn() {
        return in;
    }
    
    


    public void poveziSeSaServerom(String ip, int port) throws IOException {
         ksoket = new Socket(ip, port);
         out = new ObjectOutputStream(ksoket.getOutputStream());
         in = new ObjectInputStream(ksoket.getInputStream());
         Sesija.getSesija().getMapa().put("klijent", this);
         logavan=false;
        
    }
    
    public void pocetnaKomunikacija(String s) throws UnknownHostException, IOException, ClassNotFoundException{
        
        TOKlijentZahtev klijentZahtev = new TOKlijentZahtev();
        //predstavi se
        klijentZahtev.setZahtev(util.Util.OPERACIJA_HELO);
        klijentZahtev.setParametar(s);
        out.writeObject(klijentZahtev);
        new AsyncResponseHandler(in).start();
        
    }

    public boolean isLogavan() {
        return logavan;
    }
    
    public void ubaci (int i) throws IOException, ClassNotFoundException{
        
        TOKlijentZahtev klijentZahtev = new TOKlijentZahtev();
        //predstavi se
        klijentZahtev.setZahtev(util.Util.UBACI_ZAHTEV);
        klijentZahtev.setParametar(i);
        out.writeObject(klijentZahtev);
        
    }
    
    public void vratiNiz() throws IOException{
        TOKlijentZahtev klijentZahtev = new TOKlijentZahtev();
        //predstavi se
        klijentZahtev.setZahtev(util.Util.VRATI_NIZ);
        out.writeObject(klijentZahtev);
    }

    
    
    
   
}
