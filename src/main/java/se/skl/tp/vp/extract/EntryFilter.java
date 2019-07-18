package se.skl.tp.vp.extract;

import java.util.jar.JarEntry;

public interface EntryFilter {
  boolean filter(JarEntry entry);
}
