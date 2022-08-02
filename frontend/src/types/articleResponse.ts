import { Author } from '@/types/author';

export type Category = 'question' | 'discussion';

export interface CommonArticleType {
	id: number;
	title: string;
	author: Author;
	content: string;
	category: Category;
	commentCount: number;
	createdAt: string;
	views: number;
}

export interface ArticleType extends CommonArticleType {
	isAuthor: boolean;
	views: number;
	likeCount: number;
}

export interface AllArticleResponse {
	articles: CommonArticleType[];
	hasNext: boolean;
}

export interface infiniteArticleResponse extends AllArticleResponse {
	cursorId: number;
	cursorViews?: number;
}

export interface UserArticleItemType {
	id: number;
	title: string;
	category: string;
	commentCount: number;
	createdAt: string;
	updatedAt: string;
	views: number;
}

export interface UserArticlesResponse {
	articles: UserArticleItemType[];
}
