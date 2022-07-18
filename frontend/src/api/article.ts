import { ArticleType } from '@/types/articleResponse';
import axios from 'axios';

export interface WritingArticles {
	title: string;
	content: string;
	category: string;
}

type category = 'question' | 'discussion' | 'total';
type sort = 'latest' | 'views';

export const postWritingArticle = (article: WritingArticles) => {
	const accessToken = localStorage.getItem('accessToken');
	return axios.post('http://192.168.0.155:8080/api/articles', article, {
		headers: {
			'Access-Control-Allow-Origin': '*',
			Authorization: `Bearer ${accessToken}`,
		},
	});
};

interface PopularArticles {
	articles: ArticleType[];
	hastNext: boolean;
}

export const getPopularArticles = async () => {
	const result = await axios.get<PopularArticles>(
		`http://192.168.0.155:8080/api/articles?category=total&sort=views&page=1&size=10`,
	);
	return result.data;
};
