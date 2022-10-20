export interface ArticleTotalResponseType extends AuthorResponseType {
	id: number;
	title: string;
	tag: string[];
	content: string;
	category: 'question' | 'discussion';
	createAt: string;
	updatedAt: string;

	views: number;
	likeCount: number;
	commentCount: number;

	isAuthor: boolean;
	hasVote: boolean;
	isLike: boolean;
}

export interface AuthorResponseType {
	author: {
		name: string;
		avatarUrl: string;
	};
}

export type DetailArticleResponseType = Omit<
	ArticleTotalResponseType,
	'id' | 'category' | 'commentCount'
>;

export type SingleArticleItemResponseType = Omit<
	ArticleTotalResponseType,
	'updatedAt' | 'hasVote' | 'isAuthor'
>;

export interface TotalArticleInquiredResponseType {
	articles: SingleArticleItemResponseType[];
	hasNext: boolean;
}
