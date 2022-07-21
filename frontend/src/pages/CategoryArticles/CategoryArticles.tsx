import ArticleItem from '@/components/common/ArticleItem/ArticleItem';
import {} from 'react-query';
import { useParams } from 'react-router-dom';
import * as S from '@/pages/CategoryArticles/CategoryArticles.styles';
import SortDropdown from './SortDropdown/SortDropDown';

const CategoryArticles = () => {
	// const navigate = useNavigate();
	const { category } = useParams();
	// const { data, isLoading, isError } = useQuery('articles', getPopularArticles);

	// console.log(data);
	if (typeof category === 'undefined') {
		throw new Error('카테고리를 찾을수 없습니다.');
	}

	return (
		<S.Container>
			<S.TitleBox>
				<S.CategoryArticlesTitle category={category}>
					{category === 'discussion' ? '토론' : '에러'}
				</S.CategoryArticlesTitle>
				<SortDropdown />
			</S.TitleBox>
			<S.ArticleItemList>
				<ArticleItem />
				<ArticleItem />
				<ArticleItem />
				<ArticleItem />
			</S.ArticleItemList>
		</S.Container>
	);
};

export default CategoryArticles;
