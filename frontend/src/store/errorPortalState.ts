import { atom } from 'recoil';

export const errorPortalState = atom({
	key: 'errorPortalState',
	default: {
		isOpen: false,
	},
});
