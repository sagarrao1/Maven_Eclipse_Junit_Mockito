package com.pluralsight.security;

import java.security.Provider;
import java.security.Security;
import java.util.Iterator;
import java.util.Map;


/**
 * @author kevinj
 *
 */
public class Providers
{
    public static void main(String[] args)
    {
        Provider[] providers = Security.getProviders();
        for (int i = 0; i < providers.length; i++)
        {
            Provider provider = providers[i];
            System.out.println(provider.getName() + " " + provider.getVersionStr());

            System.out.println("------------------------------------------------------------------------------------------");

            Iterator it = provider.entrySet().iterator();
            while (it.hasNext())
            {
                Map.Entry e = (Map.Entry) it.next();
                System.out.println("\t" + e.getKey() + ": " + e.getValue());
            }
            System.out.println("------------------------------------------------------------------------------------------");
        }
    }

}
