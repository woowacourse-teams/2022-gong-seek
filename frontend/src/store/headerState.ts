import { atom } from 'recoil';

export const headerState = atom<boolean>({
	key: 'headerActive',
	default: false,
});
