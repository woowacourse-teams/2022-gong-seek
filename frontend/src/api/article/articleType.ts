import { SingleTempArticleItemResponseType } from '@/api/tempArticle/tempArticleType';

export type AuthorType = { name: string; avatarUrl: string };

export type CategoryType = 'question' | 'discussion';

export interface ArticleTotalType {
	id: number;
	title: string;
	tag: string[];
	content: string;
	category: CategoryType;
	createdAt: string;
	updatedAt: string;
	author: AuthorType;

	views: number;
	likeCount: number;
	commentCount: number;

	isAuthor: boolean;
	hasVote: boolean;
	isLike: boolean;
}

export type DetailArticleResponseType = Omit<ArticleTotalType, 'category' | 'commentCount'>;

export type SingleArticleItemResponseType = Omit<
	ArticleTotalType,
	'updatedAt' | 'hasVote' | 'isAuthor'
>;

export interface TotalArticleInquiredResponseType {
	articles: SingleArticleItemResponseType[];
	hasNext: boolean;
}

export interface InfiniteArticleResponseType extends TotalArticleInquiredResponseType {
	cursorId: string;
	cursorViews?: string;
	cursorLikes?: string;
}

export interface SingleMyPageUserArticleResponseType extends SingleTempArticleItemResponseType {
	commentCount: number;
	updatedAt: string;
	views: number;
}

export interface MyPageUserArticleResponseType {
	articles: SingleMyPageUserArticleResponseType[];
}

export type CreateArticleResponseType = Pick<ArticleTotalType, 'id'>;
export type UpdateArticleResponseType = Pick<ArticleTotalType, 'id'>;

export interface ArticleTotalRequestType {
	title: string;
	content: string;
	category: CategoryType;
	tag: string[];
	isAnonymous: boolean;
	tempArticleId: number | '';
	id: string;
}

export type UpdateArticleRequestType = Pick<
	ArticleTotalRequestType,
	'title' | 'content' | 'tag' | 'id'
>;

export type CreateArticleRequestType = Omit<ArticleTotalRequestType, 'id'>;
