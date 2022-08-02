import { atom } from 'recoil';

export const searchState = atom({
	key: 'searchState',
	default: {
		isSearchOpen: false,
		isSearching: false,
		target: '',
	},
});
