package com.space.controller;

import com.space.model.Ship;
import com.space.repository.ShipRepository;
import com.space.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest")
public class ShipController {
    @Autowired
    private ShipRepository shipRepository;
    @Autowired
    private ShipService shipService;

    @GetMapping(value = "/ships")
    public List<Ship> getSortedShipsList(@RequestParam(required = false) String name,
                                         @RequestParam(required = false) String planet,
                                         @RequestParam(required = false) String shipType,
                                         @RequestParam(required = false) Long after,
                                         @RequestParam(required = false) Long before,
                                         @RequestParam(required = false) Boolean isUsed,
                                         @RequestParam(required = false) Double minSpeed,
                                         @RequestParam(required = false) Double maxSpeed,
                                         @RequestParam(required = false) Integer minCrewSize,
                                         @RequestParam(required = false) Integer maxCrewSize,
                                         @RequestParam(required = false) Double minRating,
                                         @RequestParam(required = false) Double maxRating,
                                         @RequestParam(required = false) Integer pageNumber,
                                         @RequestParam(required = false) Integer pageSize,
                                         @RequestParam(required = false) String order) {

        List<Ship> filteredList = shipService.getFilterShips(name, planet, shipType, after, before, isUsed, minSpeed, maxSpeed,
                minCrewSize, maxCrewSize, minRating, maxRating);

        return shipService.getSortShipsList(filteredList, pageNumber, pageSize, order);
    }

    @GetMapping(value = "/ships/count")
    public Integer count(@RequestParam(required = false) String name,
                         @RequestParam(required = false) String planet,
                         @RequestParam(required = false) String shipType,
                         @RequestParam(required = false) Long after,
                         @RequestParam(required = false) Long before,
                         @RequestParam(required = false) Boolean isUsed,
                         @RequestParam(required = false) Double minSpeed,
                         @RequestParam(required = false) Double maxSpeed,
                         @RequestParam(required = false) Integer minCrewSize,
                         @RequestParam(required = false) Integer maxCrewSize,
                         @RequestParam(required = false) Double minRating,
                         @RequestParam(required = false) Double maxRating) {

        return shipService.getFilterShips(name, planet, shipType, after, before, isUsed, minSpeed, maxSpeed,
                minCrewSize, maxCrewSize, minRating, maxRating).size();
    }

    @GetMapping(value = "/ships/{id}")
    public Ship getShip(@PathVariable Long id) {
        return shipService.getShipById(id);
    }

    @PostMapping(value = "/ships/")
    public @ResponseBody
    Ship createShip
            (@RequestBody Ship jsonBody) {
        Ship ship = shipService.prepareBodyToCreate(jsonBody);
        shipRepository.save(ship);
        return ship;
    }

    @PostMapping(path = "/ships/{id}")
    public @ResponseBody
    Ship updateShip
            (@RequestBody Ship jsonBody, @PathVariable Long id) {
        Ship shipToUpdate = shipService.getShipById(id);
        if (shipService.updateShip(shipToUpdate, jsonBody)) {
            shipRepository.save(shipToUpdate);
        }
        return shipToUpdate;
    }

    @DeleteMapping(value = "/ships/{id}")
    public void deleteShip(@PathVariable Long id) {
        shipRepository.delete(getShip(id));
    }


}
