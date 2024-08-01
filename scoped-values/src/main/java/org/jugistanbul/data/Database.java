package org.jugistanbul.data;

import static org.jugistanbul.handler.RequestHandler.PRINCIPAL;

/**
 * @author hakdogan (huseyin.akdogan@patikaglobal.com)
 * Created on 1.08.2024
 ***/
public class Database {

    public static boolean access(){
        var authority = PRINCIPAL.get();
        return "admin".equals(authority.username());
    }
}
