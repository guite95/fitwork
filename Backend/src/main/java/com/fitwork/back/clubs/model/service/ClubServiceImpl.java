package com.fitwork.back.clubs.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fitwork.back.clubs.model.dto.Club;
import com.fitwork.back.clubs.model.dto.ClubFile;
import com.fitwork.back.clubs.model.repository.ClubRepository;

@Service
public class ClubServiceImpl implements ClubService {

    private final ClubRepository clubRepository;
    public ClubServiceImpl(ClubRepository clubRepository) {
        this.clubRepository = clubRepository;
    }

    @Override
    public List<Club> clubList() {
        return clubRepository.selectAllClub();
    }

    @Override
    public Club clubDetail(int clubNo) {
    	Club club = clubRepository.selectClubByClubNo(clubNo);
    	
    	ClubFile clubFile = clubRepository.selectClubFileByClubNo(clubNo);
    	
    	if (clubFile != null) club.setClubFile(clubFile);
    	
    	return club;
    }

    @Override
    public List<Club> clubByLocation(String location) {
        return clubRepository.selectClubByLocation(location);
    }

    @Override
    public List<Club> clubByCategory(String category) {
        return clubRepository.selectClubByCategory(category);
    }

    @Override
    public List<Club> clubByGender(String gender) {
        return clubRepository.selectClubByGender(gender);
    }

    @Override
    public List<Club> registeredClub(String id) {
        return clubRepository.selectRegisteredClubById(id);
    }

    @Override
    public List<Club> memberedClub(String id) {
        return clubRepository.selectMemberedClubById(id);
    }

    @Override
    public List<Club> leaderedClub(String leader) {
        return clubRepository.selectClubByLeader(leader);
    }

    @Override
    public void registClub(Club club) {
        clubRepository.insertClub(club);

        ClubFile clubFile = club.getClubFile();
        if (clubFile != null && clubFile.getOriName() != null && !clubFile.getOriName().isEmpty()) {
            clubFile.setClubNo(club.getClubNo());
            clubRepository.insertClubFile(clubFile);
        }
    }

    @Override
    public void addToRegisteredClub(String id, int clubNo) {
        clubRepository.insertClubRegister(id, clubNo);
    }

    @Override
    public void permitMemberToClub(String id, int clubNo) {
        clubRepository.deleteClubRegister(id, clubNo);
        clubRepository.insertClubMember(id, clubNo);
    }

    @Override
    public void exitFromClub(String id, int clubNo) {
        clubRepository.deleteClubMember(id, clubNo);
    }

    @Override
    public void refuseRegister(String id, int clubNo) {
        clubRepository.deleteClubRegister(id, clubNo);
    }

    @Override
    public void modifyClubInfo(Club club) {
    	club.getClubFile().setClubNo(club.getClubNo());
    	clubRepository.insertClubFile(club.getClubFile());
        clubRepository.updateClubInfo(club);
    }
    
    public void deleteClubFile(int fileNo) {
    	clubRepository.deleteClubFile(fileNo);
    }

    @Override
    public void deleteClubInfo(int clubNo) {
        clubRepository.deleteClub(clubNo);
    }

    @Override
    public String checkIfClubIsEmpty(List<Club> list) {
        if (list.isEmpty()) {
            return "클럽이 없습니다";
        }
        return null;
    }
}
