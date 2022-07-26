import { atom } from 'recoil';

export const articleState = atom({
	key: 'articleState',
	default: {
		title: '',
		content: '',
	},
});
