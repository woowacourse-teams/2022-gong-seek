import PopularArticle from '@/pages/Home//PopularArticle/PopularArticle';
import { useState } from 'react';
import styled from '@emotion/styled';
import { css } from '@emotion/react';
import ArticleItem from '@/components/common/ArticleItem/ArticleItem';
import SortDropdown from '@/pages/CategoryArticles/SortDropdown/SortDropDown';

const CategoryTitleContainer = styled.div`
	display: flex;
	justify-content: space-between;
	align-items: center;
	margin-top: ${({ theme }) => theme.size.SIZE_050};
`;

const CategoryTitleBox = styled.div`
	display: flex;
	gap: ${({ theme }) => theme.size.SIZE_012};
	align-items: center;
`;

const CategoryTitle = styled.div<{ isActive: boolean }>`
	font-size: ${({ theme }) => theme.size.SIZE_018};
	font-weight: 600;
	color: ${({ theme }) => theme.colors.BLACK_600};
	opacity: 0.3;
	cursor: pointer;

	${({ isActive, theme }) =>
		isActive &&
		css`
			color: ${theme.colors.PURPLE_500};
			text-decoration: underline;
			opacity: 1;
		`}
`;

const Container = styled.div`
	display: flex;
	flex-direction: column;
`;

const PopularArticleTitle = styled.h2`
	font-size: ${({ theme }) => theme.size.SIZE_012};
	font-weight: 600;
	color: ${({ theme }) => theme.colors.BLACK_600};
	margin-bottom: ${({ theme }) => theme.size.SIZE_030};
`;

const ArticleItemList = styled.div`
	display: flex;
	flex-direction: column;
	align-items: center;
	gap: ${({ theme }) => theme.size.SIZE_018};
	width: 100%;
	margin-top: ${({ theme }) => theme.size.SIZE_024};
`;

const Home = () => {
	const [currentCategory, setCurrentCategory] = useState('question');

	return (
		<Container>
			<PopularArticleTitle>오늘의 인기글</PopularArticleTitle>
			<PopularArticle />
			<CategoryTitleContainer>
				<CategoryTitleBox>
					<CategoryTitle
						isActive={currentCategory === 'question'}
						onClick={() => setCurrentCategory('question')}
					>
						에러
					</CategoryTitle>
					<CategoryTitle
						isActive={currentCategory === 'discussion'}
						onClick={() => setCurrentCategory('discussion')}
					>
						토론
					</CategoryTitle>
				</CategoryTitleBox>
				<SortDropdown />
			</CategoryTitleContainer>
			<ArticleItemList>
				<ArticleItem />
				<ArticleItem />
				<ArticleItem />
				<ArticleItem />
				<ArticleItem />
			</ArticleItemList>
		</Container>
	);
};

export default Home;
