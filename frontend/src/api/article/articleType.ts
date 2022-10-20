export interface AuthorType {
	author: {
		name: string;
		avatarUrl: string;
	};
}

export type CategoryType = 'question' | 'discussion';

export interface ArticleTotalType extends AuthorType {
	id: number;
	title: string;
	tag: string[];
	content: string;
	category: CategoryType;
	createAt: string;
	updatedAt: string;

	views: number;
	likeCount: number;
	commentCount: number;

	isAuthor: boolean;
	hasVote: boolean;
	isLike: boolean;
}

export type DetailArticleResponseType = Omit<ArticleTotalType, 'id' | 'category' | 'commentCount'>;

export type SingleArticleItemResponseType = Omit<
	ArticleTotalType,
	'updatedAt' | 'hasVote' | 'isAuthor'
>;

export interface TotalArticleInquiredResponseType {
	articles: SingleArticleItemResponseType[];
	hasNext: boolean;
}

export type SingleTempArticleItemResponseType = Pick<
	ArticleTotalType,
	'id' | 'title' | 'createAt' | 'category'
>;

export interface TotalTempArticleResponseType {
	values: SingleTempArticleItemResponseType[];
}

export type CreateArticleResponseType = Pick<ArticleTotalType, 'id'>;
export type UpdateArticleResponseType = Pick<ArticleTotalType, 'id'>;

export interface ArticleTotalRequestType {
	title: string;
	content: string;
	category: CategoryType;
	tag: string[];
	isAnonymous: boolean;
	tempArticleId: number;
}

export type UpdateArticleRequestType = Pick<ArticleTotalRequestType, 'title' | 'content' | 'tag'>;
