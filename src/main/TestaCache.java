package main;

import memoria.fisica.cache.MemoriaCache;

public class TestaCache {
    public static void main(String[] args){
        MemoriaCache cache = new MemoriaCache(8,2, 8, null);

        System.out.println(Integer.toBinaryString(cache.getTagNum( 171)));
        System.out.println(Integer.toBinaryString(cache.getLineNum( 171)));
        System.out.println(Integer.toBinaryString(cache.getOffset( 171)));
    }
}
