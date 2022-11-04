import {
	DetailArticleResponseType,
	TotalArticleInquiredResponseType,
} from '@/api/article/articleType';
import { convertSort } from '@/utils/converter';
import { generateAxiosInstanceWithAccessToken } from '@/utils/generateAxiosInstance';

export interface WritingArticlesProp {
	title: string;
	content: string;
	category: string;
}

export const postWritingArticle = (article: WritingArticlesProp) => {
	const axiosInstance = generateAxiosInstanceWithAccessToken();
	return axiosInstance.post('/api/articles', article);
};

export const getPopularArticles = async () => {
	const axiosInstance = generateAxiosInstanceWithAccessToken();
	const { data } = await axiosInstance.get<TotalArticleInquiredResponseType>(
		'/api/articles?category=all&sort=views&size=10',
	);
	return data;
};

export const getDetailArticle = async (id: string) => {
	const axiosInstance = generateAxiosInstanceWithAccessToken();
	const { data } = await axiosInstance.get<DetailArticleResponseType>(`/api/articles/${id}`);

	return data;
};

interface getArticleProps {
	category: string;
	sort: '추천순' | '조회순' | '최신순';
	cursorId: string;
	cursorViews: string;
	cursorLikes: string;
}

export const getAllArticle = async ({
	category,
	sort,
	cursorId,
	cursorViews,
	cursorLikes,
}: getArticleProps) => {
	if (sort === '추천순') {
		const data = await getAllArticlesByLikes({ category, cursorId, cursorLikes });
		return data;
	}

	const data = await getAllArticleByViewsOrLatest({
		category,
		sort,
		cursorId,
		cursorViews,
	});
	return data;
};

interface getAllArticleByViewsOrLatestProps {
	category: string;
	sort: '추천순' | '조회순' | '최신순';
	cursorId: string;
	cursorViews: string;
}

export const getAllArticleByViewsOrLatest = async ({
	category,
	sort,
	cursorId,
	cursorViews,
}: getAllArticleByViewsOrLatestProps) => {
	const currentSort = convertSort(sort);
	const axiosInstance = generateAxiosInstanceWithAccessToken();
	const { data } = await axiosInstance.get<TotalArticleInquiredResponseType>(
		`/api/articles?category=${category}&sort=${currentSort}&cursorId=${cursorId}&cursorViews=${cursorViews}&size=6`,
	);
	return {
		articles: data.articles,
		hasNext: data.hasNext,
		cursorId:
			data.articles && data.articles[data.articles.length - 1]
				? String(data.articles[data.articles.length - 1].id)
				: '',
		cursorViews:
			data.articles && data.articles[data.articles.length - 1]
				? String(data.articles[data.articles.length - 1].views)
				: '',
		cursorLikes:
			data.articles && data.articles[data.articles.length - 1]
				? String(data.articles[data.articles.length - 1].likeCount)
				: '',
	};
};

interface getAllArticlesByLikesProps {
	category: string;
	cursorId: string;
	cursorLikes: string;
}

export const getAllArticlesByLikes = async ({
	category,
	cursorId,
	cursorLikes = '',
}: getAllArticlesByLikesProps) => {
	const axiosInstance = generateAxiosInstanceWithAccessToken();

	const { data } = await axiosInstance.get<TotalArticleInquiredResponseType>(
		`/api/articles/likes?category=${category}&cursorId=${cursorId}&cursorLikes=${cursorLikes}&size=6`,
	);

	return {
		articles: data.articles,
		hasNext: data.hasNext,
		cursorId:
			data && data.articles[data.articles.length - 1]
				? String(data.articles[data.articles.length - 1].id)
				: '',
		cursorLikes:
			data && data.articles[data.articles.length - 1]
				? String(data.articles[data.articles.length - 1].likeCount)
				: '',
		cursorViews:
			data.articles && data.articles[data.articles.length - 1]
				? String(data.articles[data.articles.length - 1].views)
				: '',
	};
};

export const postArticle = (article: { id: string; title: string; content: string }) => {
	const axiosInstance = generateAxiosInstanceWithAccessToken();
	return axiosInstance.post<{ id: number; category: string }>(`/api/articles/${article.id}`, {
		title: article.title,
		content: article.content,
	});
};

export const putArticle = (article: {
	id: string;
	title: string;
	content: string;
	tag: string[];
}) => {
	const axiosInstance = generateAxiosInstanceWithAccessToken();
	return axiosInstance.put<{ id: number; category: string }>(`/api/articles/${article.id}`, {
		title: article.title,
		content: article.content,
		tag: article.tag,
	});
};

export const deleteArticle = (id: string) => {
	const axiosInstance = generateAxiosInstanceWithAccessToken();
	return axiosInstance.delete<never, unknown, unknown>(`/api/articles/${id}`);
};
