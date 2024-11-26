package com.fitwork.back.clubs.model.repository;

import java.util.List;

import com.fitwork.back.clubs.model.dto.Club;
import com.fitwork.back.clubs.model.dto.ClubFile;

public interface ClubRepository {
    public List<Club> selectAllClub();
    
    public Club selectClubByClubNo(int clubNo);
    
    public ClubFile selectClubFileByClubNo(int clubNo);
    
    public List<Club> selectClubByLocation(String location);
    
    public List<Club> selectClubByCategory(String category);
    
    public List<Club> selectClubByGender(String gender);
    
    public List<Club> selectRegisteredClubById(String id);
    
    public List<Club> selectMemberedClubById(String id);
    
    public List<Club> selectClubByLeader(String leader);
    
    public void insertClub(Club club);
    
    public void insertClubFile(ClubFile clubFile);
    
    public void insertClubRegister(String id, int clubNo);
    
    public void deleteClubRegister(String id, int clubNo);
    
    public boolean isRegisted(String id, int clubNo);
    
    public void insertClubMember(String id, int clubNo);
    
    public void deleteClubMember(String id, int clubNo);
    
    public void updateClubInfo(Club club);
    
    public void deleteClubFile(int fileNo);
    
    public void deleteClub(int clubNo);
}
