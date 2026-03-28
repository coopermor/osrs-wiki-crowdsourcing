package com.Crowdsourcing.doom_of_mokhaiotl;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DomLootData
{
	private Map<Integer, Map<Integer, Integer>> waveLoot;
}
