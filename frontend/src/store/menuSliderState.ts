import { atom } from 'recoil';

export const menuSliderState = atom({
	key: 'menuSliderState',
	default: {
		isOpen: false,
	},
});
