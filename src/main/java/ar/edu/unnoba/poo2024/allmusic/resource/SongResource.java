package ar.edu.unnoba.poo2024.allmusic.resource;

import ar.edu.unnoba.poo2024.allmusic.dto.CreateSongRequestDTO;
import ar.edu.unnoba.poo2024.allmusic.dto.SongResponseDTO;
import ar.edu.unnoba.poo2024.allmusic.model.Genre;
import ar.edu.unnoba.poo2024.allmusic.model.MusicArtistUser;
import ar.edu.unnoba.poo2024.allmusic.model.Song;
import ar.edu.unnoba.poo2024.allmusic.model.User;
import ar.edu.unnoba.poo2024.allmusic.service.AuthorizationService;
import ar.edu.unnoba.poo2024.allmusic.service.SongService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/songs")
public class SongResource {
    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private SongService service;

    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestHeader("Authorization") String token,
            @RequestParam(value = "artist", required = false) String artistName,
            @RequestParam(value = "genre", required = false) Genre genre) {

        try {
            User user = authorizationService.authorize(token);
            List<Song> songs = service.findByArtistAndGenre(artistName, genre);
            ModelMapper modelMapper = new ModelMapper();
            List<SongResponseDTO> songDTOList = songs.stream()
                    .map(song -> modelMapper.map(song, SongResponseDTO.class))
                    .collect(Collectors.toList());
            return new ResponseEntity<>(songDTOList, HttpStatus.OK);
        } catch (Exception e) {
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            return new ResponseEntity<>(map, HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity <?> getSongById(@PathVariable Long id){
        try {
            Optional<Song> song = service.getInstance(id);
            if(song.isEmpty()){
                MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
                return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
            }
            ModelMapper modelMapper = new ModelMapper();
            SongResponseDTO songDTO = modelMapper.map(song.get(), SongResponseDTO.class);
            return new ResponseEntity<>(songDTO, HttpStatus.OK);
        }  catch (Exception e) {
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity <?> removeSongById(@PathVariable Long id, @RequestHeader("Authorization") String token){
        User user;
        Optional <Song> song;
        MultiValueMap<String, String> emptyResponse = new LinkedMultiValueMap<>();
        try {
            user = authorizationService.authorize(token);
        } catch (Exception e) {
            return new ResponseEntity<>(emptyResponse, HttpStatus.FORBIDDEN);
        }
        try {
            song = service.getInstance(id);
            if(song.isEmpty()) {
                MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
                return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
            }
            if( !song.get().getAuthor().getId().equals(user.getId()) ){
                return new ResponseEntity<>(emptyResponse, HttpStatus.FORBIDDEN);
            }
            service.delete(song.get().getId());
            ModelMapper modelMapper = new ModelMapper();
            SongResponseDTO songDTO = modelMapper.map(song, SongResponseDTO.class);
            return new ResponseEntity<>(songDTO, HttpStatus.OK);
        }  catch (Exception e) {
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestHeader("Authorization") String token, @RequestBody CreateSongRequestDTO songDTO) {
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

        ModelMapper modelMapper = new ModelMapper();
        Song song = new Song();
        song = modelMapper.map(songDTO, Song.class);
        song.setAuthor((MusicArtistUser) user);
        try {
            service.create(song);
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
