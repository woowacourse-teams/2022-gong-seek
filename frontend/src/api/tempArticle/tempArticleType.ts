import { ArticleTotalType } from '@/api/article/articleType';

export type SingleTempArticleItemResponseType = Pick<
	ArticleTotalType,
	'id' | 'title' | 'createdAt' | 'category'
>;

export interface TotalTempArticleResponseType {
	values: SingleTempArticleItemResponseType[];
}

export interface DetailTempArticleResponseType extends SingleTempArticleItemResponseType {
	tag: string[];
	content: string;
	isAnonymous: boolean;
}
