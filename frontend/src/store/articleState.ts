import { atom } from 'recoil';

interface ArticleStateType {
	title: string;
	content: string;
	tag: string[];
}

export const articleState = atom<ArticleStateType>({
	key: 'articleState',
	default: {
		title: '',
		content: '',
		tag: [],
	},
});
