import { atom } from 'recoil';

//TODO:미리 만들어진 interface에서 골라쓰자!
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
