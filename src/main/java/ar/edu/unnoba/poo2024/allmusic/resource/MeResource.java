package ar.edu.unnoba.poo2024.allmusic.resource;

import ar.edu.unnoba.poo2024.allmusic.dto.PlaylistResponseDTO;
import ar.edu.unnoba.poo2024.allmusic.dto.SongResponseDTO;
import ar.edu.unnoba.poo2024.allmusic.model.MusicArtistUser;
import ar.edu.unnoba.poo2024.allmusic.model.Playlist;
import ar.edu.unnoba.poo2024.allmusic.model.Song;
import ar.edu.unnoba.poo2024.allmusic.model.User;
import ar.edu.unnoba.poo2024.allmusic.service.AuthorizationService;
import ar.edu.unnoba.poo2024.allmusic.service.PlaylistService;
import ar.edu.unnoba.poo2024.allmusic.service.SongService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/me")
public class MeResource {
    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private SongService songService;

    @Autowired
    private PlaylistService playlistService;

    @GetMapping("/songs")
    public ResponseEntity<?> getSongs(@RequestHeader("Authorization") String token) {
        User user;
        MultiValueMap<String, String> emptyResponse = new LinkedMultiValueMap<>();
        try {
            user = authorizationService.authorize(token);
        } catch (Exception e) {
            return new ResponseEntity<>(emptyResponse, HttpStatus.FORBIDDEN);
        }

        if(!user.canCreateSongs()){
            return new ResponseEntity<>(emptyResponse, HttpStatus.FORBIDDEN);
        }
        try {
            ModelMapper modelMapper = new ModelMapper();
            List<Song> songs = songService.getAllFromUser(user.getId());
            List<SongResponseDTO> songDTOList = songs.stream().map(song -> modelMapper.map(song, SongResponseDTO.class)).toList();;
            return new ResponseEntity<>(songDTOList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/playlists")
    public ResponseEntity<?> getPlaylists(@RequestHeader("Authorization") String token) {
        User user;
        MultiValueMap<String, String> emptyResponse = new LinkedMultiValueMap<>();
        try {
            user = authorizationService.authorize(token);
        } catch (Exception e) {
            return new ResponseEntity<>(emptyResponse, HttpStatus.FORBIDDEN);
        }

        if(!user.canCreateSongs()){
            return new ResponseEntity<>(emptyResponse, HttpStatus.FORBIDDEN);
        }
        try {
            ModelMapper modelMapper = new ModelMapper();
            List<Playlist> playlists = playlistService.getAllFromUser(user.getId());
            List<PlaylistResponseDTO> songDTOList = playlists.stream().map(playlist -> modelMapper.map(playlist, PlaylistResponseDTO.class)).toList();;
            return new ResponseEntity<>(songDTOList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
