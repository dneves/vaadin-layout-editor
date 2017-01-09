package com.neon.vaadin.upload;

import java.io.File;

public interface CacheProvider {

    < ERROR extends Exception > File createTempFile( String filename ) throws ERROR;

}
