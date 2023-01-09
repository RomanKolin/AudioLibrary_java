package com.example.audiolibrary;

import java.sql.*;
import java.util.Arrays;

public class AudioLibraryDB
{
    public static Connection conn;

    public static Connection conn() throws Exception
    {
        Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
        conn = DriverManager.getConnection("jdbc:mysql://localhost:/Audio library", "romankolin", "IT2017year");

        return conn;
    }

    public static String[][] SELECT(int tabl) throws Exception
    {
        int count = 0;

        conn();
        Statement stat = conn.createStatement();
        ResultSet resset;
        String[][] datarr = new String[0][0];
        switch (tabl)
        {
            case 1:
                resset = stat.executeQuery("SELECT COUNT(cat) FROM `Audio library`");
                while (resset.next())
                    count = resset.getInt("COUNT(cat)");
                datarr = new String[count][3];

                resset = stat.executeQuery("SELECT * FROM `Audio library` ORDER BY cat");
                count = 0;
                while (resset.next())
                {
                    String cat = resset.getString(1);
                    int noorigartsbands = resset.getInt(2);
                    int nosongs = resset.getInt(3);
                    datarr[count][0] = cat;
                    datarr[count][1] = String.valueOf(noorigartsbands);
                    datarr[count][2] = String.valueOf(nosongs);

                    count += 1;
                }
                break;
            case 2:
                resset = stat.executeQuery("SELECT COUNT(nam) FROM Genre");
                while (resset.next())
                    count = resset.getInt("COUNT(nam)");
                datarr = new String[count][3];

                resset = stat.executeQuery("SELECT * FROM Genre ORDER BY nam");
                count = 0;
                while (resset.next())
                {
                    String nam = resset.getString(1);
                    int noartsbands = resset.getInt(2);
                    int nosongs = resset.getInt(3);
                    datarr[count][0] = nam;
                    datarr[count][1] = String.valueOf(noartsbands);
                    datarr[count][2] = String.valueOf(nosongs);

                    count += 1;
                }
                break;
            case 3:
                resset = stat.executeQuery("SELECT COUNT(ID) FROM `Music artist/band`");
                while (resset.next())
                    count = resset.getInt("COUNT(ID)");
                datarr = new String[count][4];

                resset = stat.executeQuery("SELECT * FROM `Music artist/band` ORDER BY artband");
                count = 0;
                while (resset.next())
                {
                    String artband = resset.getString(2);
                    String relartband = resset.getString(3);
                    String genr = resset.getString(4);
                    int nosongs = resset.getInt(5);
                    datarr[count][0] = artband;
                    datarr[count][1] = relartband;
                    datarr[count][2] = genr;
                    datarr[count][3] = String.valueOf(nosongs);

                    count += 1;
                }
                break;
            case 4:
                resset = stat.executeQuery("SELECT COUNT(ID) FROM Composers");
                while (resset.next())
                    count = resset.getInt("COUNT(ID)");
                datarr = new String[count][2];

                resset = stat.executeQuery("SELECT * FROM Composers ORDER BY nam");
                count = 0;
                while (resset.next())
                {
                    String nam = resset.getString(2);
                    int nosongs = resset.getInt(3);
                    datarr[count][0] = nam;
                    datarr[count][1] = String.valueOf(nosongs);

                    count += 1;
                }
                break;
            case 5:
                resset = stat.executeQuery("SELECT COUNT(ID) FROM Bloggers");
                while (resset.next())
                    count = resset.getInt("COUNT(ID)");
                datarr = new String[count][2];

                resset = stat.executeQuery("SELECT * FROM Bloggers ORDER BY nicknam");
                count = 0;
                while (resset.next())
                {
                    String nicknam = resset.getString(2);
                    int nosongs = resset.getInt(3);
                    datarr[count][0] = nicknam;
                    datarr[count][1] = String.valueOf(nosongs);

                    count += 1;
                }
                break;
            case 6:
                resset = stat.executeQuery("SELECT COUNT(artband) FROM Covers");
                while (resset.next())
                    count = resset.getInt("COUNT(artband)");
                datarr = new String[count][4];

                resset = stat.executeQuery("SELECT * FROM Covers ORDER BY artband");
                count = 0;
                while (resset.next())
                {
                    String artband = resset.getString(2);
                    String feat = resset.getString(3);
                    int nosongs = resset.getInt(4);
                    String origartband = resset.getString(5);
                    datarr[count][0] = artband;
                    datarr[count][1] = feat;
                    datarr[count][2] = String.valueOf(nosongs);
                    datarr[count][3] = origartband;

                    count += 1;
                }
                break;
            case 7:
                resset = stat.executeQuery("SELECT COUNT(ID) FROM Soundtracks");
                while (resset.next())
                    count = resset.getInt("COUNT(ID)");
                datarr = new String[count][3];

                resset = stat.executeQuery("SELECT * FROM Soundtracks ORDER BY movanimser");
                count = 0;
                while (resset.next())
                {
                    String movanimser = resset.getString(2);
                    String artband = resset.getString(3);
                    int nosongs = resset.getInt(4);
                    datarr[count][0] = movanimser;
                    datarr[count][1] = artband;
                    datarr[count][2] = String.valueOf(nosongs);

                    count += 1;
                }
                break;
            case 8:
                resset = stat.executeQuery("SELECT COUNT(artband) FROM Favourites");
                while (resset.next())
                    count = resset.getInt("COUNT(artband)");
                datarr = new String[count][6];

                resset = stat.executeQuery("SELECT * FROM Favourites ORDER BY num");
                count = 0;
                while (resset.next())
                {
                    int num = resset.getInt(1);
                    String artband = resset.getString(2);
                    String relartband = resset.getString(3);
                    String genr = resset.getString(4);
                    int nosongs = resset.getInt(5);

                    datarr[count][0] = String.valueOf(num);
                    datarr[count][1] = artband;
                    datarr[count][2] = relartband;
                    datarr[count][3] = genr;
                    datarr[count][4] = String.valueOf(nosongs);

                    count += 1;
                }
                break;

        }
        conn.close();

        return datarr;
    }

    public static void PreparedStatement(String stat, PreparedStatement pstat, String[] datarr, int itim) throws Exception
    {
        if (stat.equals("INSERT"))
            for (int i = 1; i <= itim; i++)
                if (String.valueOf(Arrays.asList(datarr).get(i - 1)).equals(""))
                    pstat.setString(i, null);
                else
                    pstat.setString(i, Arrays.asList(datarr).get(i - 1));
        else if (stat.equals("UPDATE"))
        {
            for (int i = 1; i <= itim; i++)
                if (String.valueOf(Arrays.asList(datarr).get(i)).equals(""))
                    pstat.setString(i, null);
                else
                    pstat.setString(i, Arrays.asList(datarr).get(i));
            pstat.setString(itim, Arrays.asList(datarr).get(0));
        }
    }

    public static int row;

    public static String INSERT(int tabl, String[] datarr) throws Exception
    {
        conn();
        PreparedStatement pstat = null;
        switch (tabl)
        {
            case 3:
                pstat = conn.prepareStatement("INSERT INTO `Music artist/band`(artband, relartband, genr, nosongs) VALUES(?, ?, ?, ?)");
                PreparedStatement("INSERT", pstat, datarr, 4);
                break;
            case 4:
                pstat = conn.prepareStatement("INSERT INTO Composers(nam, nosongs) VALUES(?, ?)");
                PreparedStatement("INSERT", pstat, datarr, 2);
                break;
            case 5:
                pstat = conn.prepareStatement("INSERT INTO Bloggers(nicknam, nosongs) VALUES(?, ?)");
                PreparedStatement("INSERT", pstat, datarr, 2);
                break;
            case 6:
                pstat = conn.prepareStatement("INSERT INTO Covers(artband, feat, nosongs, origartband) VALUES(?, ?, ?, ?)");
                PreparedStatement("INSERT", pstat, datarr, 4);
                break;
            case 7:
                pstat = conn.prepareStatement("INSERT INTO Soundtracks(movanimser, artband, nosongs) VALUES(?, ?, ?)");
                PreparedStatement("INSERT", pstat, datarr, 3);
                break;
        }
        try
        {
            row = pstat.executeUpdate();
        }
        catch (Exception e)
        {
            row = 0;
        }
        conn.close();

        if (row > 0)
            return "Your data has been saved";
        else
            return "Your data hasn't been saved";
    }

    public static String UPDATE(int tabl, String[] datarr) throws Exception
    {
        conn();
        PreparedStatement pstat = null;
        switch (tabl)
        {
            case 3:
                pstat = conn.prepareStatement("UPDATE `Music artist/band` SET relartband=?, genr=?, nosongs=? WHERE artband=?");
                PreparedStatement("UPDATE", pstat, datarr, 4);
                break;
            case 4:
                pstat = conn.prepareStatement("UPDATE Composers SET nosongs=? WHERE nam=?");
                PreparedStatement("UPDATE", pstat, datarr, 2);
                break;
            case 5:
                pstat = conn.prepareStatement("UPDATE Bloggers SET nosongs=? WHERE nicknam=?");
                PreparedStatement("UPDATE", pstat, datarr, 2);
                break;
            case 6:
                pstat = conn.prepareStatement("UPDATE Covers SET feat=?, nosongs=?, origartband=? WHERE artband=?");
                PreparedStatement("UPDATE", pstat, datarr, 4);
                break;
            case 7:
                pstat = conn.prepareStatement("UPDATE Soundtracks SET artband=?, nosongs=? WHERE movanimser=?");
                PreparedStatement("UPDATE", pstat, datarr, 3);
                break;
        }
        try
        {
            row = pstat.executeUpdate();
        }
        catch (Exception e)
        {
            row = 0;
        }
        conn.close();

        if (row > 0)
            return "Your data has been updated";
        else
            return "Your data hasn't been updated";
    }
}