package com.govdevchallenge.team11.challenge2.events;

import com.govdevchallenge.team11.challenge2.models.Item;

/**
 * Created by PaulTR on 5/17/14.
 */
public class AddItemEvent {
	public Item item;

	public AddItemEvent( Item item ) {
		this.item = item;
	}
}
