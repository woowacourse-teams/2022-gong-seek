import { atom } from 'recoil';

interface ArticleStateType {
	title: string;
	content: string;
	hashTag: string[];
}

export const articleState = atom<ArticleStateType>({
	key: 'articleState',
	default: {
		title: '',
		content: '',
		hashTag: [],
	},
});
