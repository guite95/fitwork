package com.fitwork.back.clubs.model.service;

import java.util.List;

import com.fitwork.back.clubs.model.dto.Club;

public interface ClubService {
	
    public List<Club> clubList();
    
    public Club clubDetail(int clubNo);
    
    public String checkIfClubIsEmpty(List<Club> list);
    
    public List<Club> clubByLocation(String location);
    
    public List<Club> clubByCategory(String category);
    
    public List<Club> clubByGender(String gender);
    
    public List<Club> registeredClub(String id);
    
    public List<Club> memberedClub(String id);
    
    public List<Club> leaderedClub(String leader);
    
    public void registClub(Club club);
    
    public void addToRegisteredClub(String id, int clubNo);
    
    public void permitMemberToClub(String id, int clubNo);
    
    public void exitFromClub(String id, int clubNo);
    
    public void refuseRegister(String id, int clubNo);
    
    public void modifyClubInfo(Club club);
    
    public void deleteClubInfo(int clubNo);
    
}
