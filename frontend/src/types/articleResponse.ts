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
	hashtag: string[];
	isLike: boolean;
	likeCount: number;

}

export interface ArticleType extends CommonArticleType {
	isAuthor: boolean;
}

export interface AllArticleResponse {
	articles: CommonArticleType[];
	hasNext: boolean;
}

export interface infiniteArticleResponse extends AllArticleResponse {
	cursorId: string;
	cursorViews?: string;
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
