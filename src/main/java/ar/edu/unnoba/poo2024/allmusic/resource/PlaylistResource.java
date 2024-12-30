package ar.edu.unnoba.poo2024.allmusic.resource;

import ar.edu.unnoba.poo2024.allmusic.dto.*;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/playlists")
public class PlaylistResource {
    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private PlaylistService service;

    @Autowired
    private SongService songService;


    @GetMapping
    public ResponseEntity<?> getAll(@RequestHeader("Authorization") String token){
        User user;
        try {
            user = authorizationService.authorize(token);
            List<Playlist> playlists = service.getAll();
            List<PlaylistSimplifiedResponseDTO> playlistDTOList = new ArrayList<>();
            for (Playlist p: playlists) {
                PlaylistSimplifiedResponseDTO response = new PlaylistSimplifiedResponseDTO();
                response.setName(p.getName());
                response.setSongCount(p.getSongs().size());
                playlistDTOList.add(response);
            }
            return new ResponseEntity<>(playlistDTOList, HttpStatus.OK);
        } catch (Exception e) {
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            return new ResponseEntity<>(map, HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/{id}/songs")
    public ResponseEntity<?> addSong(@PathVariable Long id, @RequestHeader("Authorization") String token, @RequestBody AddSongToPlaylistRequestDTO addSongDTO) {
        User user;
        Optional<Playlist>  playlist;
        Optional<Song> song;
        MultiValueMap<String, String> emptyResponse = new LinkedMultiValueMap<>();
        try {
            user = authorizationService.authorize(token);
        } catch (Exception e) {
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            return new ResponseEntity<>(map, HttpStatus.FORBIDDEN);
        }

        try {
            playlist = service.findById(id);
            if(playlist.isEmpty()) {
                return new ResponseEntity<>(emptyResponse, HttpStatus.NOT_FOUND);
            }
            Playlist currentPlaylist = playlist.get();
            if( !currentPlaylist.getOwner().getId().equals(user.getId()) ){
                return new ResponseEntity<>(emptyResponse, HttpStatus.FORBIDDEN);
            }
            song = songService.getInstance(addSongDTO.getSong_id());
            if(song.isEmpty()){
                return new ResponseEntity<>(emptyResponse, HttpStatus.BAD_REQUEST);
            }
            Song currentSong = song.get();
            service.addSong(currentPlaylist, currentSong);
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(emptyResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}/songs/{song_id}")
    public ResponseEntity<?> removeSong(@PathVariable Long id, @PathVariable Long song_id, @RequestHeader("Authorization") String token) {
        User user;
        Optional<Playlist>  playlist;
        Optional<Song> song;
        MultiValueMap<String, String> emptyResponse = new LinkedMultiValueMap<>();
        try {
            user = authorizationService.authorize(token);
        } catch (Exception e) {
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            return new ResponseEntity<>(map, HttpStatus.FORBIDDEN);
        }

        try {
            playlist = service.findById(id);
            if(playlist.isEmpty()) {
                return new ResponseEntity<>(emptyResponse, HttpStatus.NOT_FOUND);
            }
            Playlist currentPlaylist = playlist.get();
            if( !currentPlaylist.getOwner().getId().equals(user.getId()) ){
                return new ResponseEntity<>(emptyResponse, HttpStatus.FORBIDDEN);
            }
            song = songService.getInstance(song_id);
            if(song.isEmpty()){
                return new ResponseEntity<>(emptyResponse, HttpStatus.BAD_REQUEST);
            }
            Song currentSong = song.get();
            service.removeSong(currentPlaylist, currentSong);
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(emptyResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestHeader("Authorization") String token, @RequestBody CreatePlaylistRequestDTO playlistDTO) {
        User user;
        Playlist playlist;
        try {
            user = authorizationService.authorize(token);
        } catch (Exception e) {
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            return new ResponseEntity<>(map, HttpStatus.FORBIDDEN);
        }

        try {
            ModelMapper modelMapper = new ModelMapper();
            playlist = modelMapper.map(playlistDTO, Playlist.class);
            playlist.setOwner(user);
            service.create(playlist);
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        } catch (Exception e) {
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestHeader("Authorization") String token, @RequestBody UpdatePlaylistRequestDTO playlistDTO) {
        User user;
        Optional<Playlist>  playlist;
        MultiValueMap<String, String> emptyResponse = new LinkedMultiValueMap<>();
        try {
            user = authorizationService.authorize(token);
        } catch (Exception e) {
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            return new ResponseEntity<>(map, HttpStatus.FORBIDDEN);
        }

        try {
            playlist = service.findById(id);
            if(playlist.isEmpty()) {
                MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
                return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
            }
            Playlist currentPlaylist = playlist.get();
            if( !currentPlaylist.getOwner().getId().equals(user.getId()) ){
                return new ResponseEntity<>(emptyResponse, HttpStatus.FORBIDDEN);
            }
            ModelMapper modelMapper = new ModelMapper();
            Playlist playlistToUpdate = modelMapper.map(playlistDTO, Playlist.class);
            currentPlaylist.setName(playlistToUpdate.getName());
            service.update(currentPlaylist);
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(emptyResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        User user;
        Optional<Playlist>  playlist;
        MultiValueMap<String, String> emptyResponse = new LinkedMultiValueMap<>();
        try {
            user = authorizationService.authorize(token);
        } catch (Exception e) {
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            return new ResponseEntity<>(map, HttpStatus.FORBIDDEN);
        }

        try {
            playlist = service.findById(id);
            if(playlist.isEmpty()) {
                MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
                return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
            }
            Playlist currentPlaylist = playlist.get();
            if( !currentPlaylist.getOwner().getId().equals(user.getId()) ){
                return new ResponseEntity<>(emptyResponse, HttpStatus.FORBIDDEN);
            }
            service.remove(currentPlaylist);
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(emptyResponse, HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }
}
