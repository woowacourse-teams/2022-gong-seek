import React, { Suspense, useState } from 'react';

import Loading from '@/components/@common/Loading/Loading';
import MyCategoryTab from '@/components/user/MyCategoryTab/MyCategoryTab';
import ArticleBox from '@/components/user/UserArticleBox/UserArticleBox';
import UserProfile from '@/components/user/UserProfile/UserProfile';
import useGetUserInfo from '@/hooks/user/useGetUserInfo';
import * as S from '@/pages/MyPage/index.styles';
import { CategoryType } from '@/types/myPage';

const CommentBox = React.lazy(() => import('@/components/user/UserCommentBox/UserCommentBox'));
const TemporaryArticleList = React.lazy(
	() => import('@/pages/TemporaryArticles/TemporaryArticleList/TemporaryArticleList'),
);

const MyPage = () => {
	const { data: info, isSuccess: isInfoSuccess, isLoading: isInfoLoading } = useGetUserInfo();

	const [category, setCategory] = useState<CategoryType>('article');

	if (isInfoLoading) {
		return <Loading />;
	}

	return (
		<S.Container>
			<S.Title>마이 페이지</S.Title>
			{isInfoSuccess && info ? (
				<>
					<UserProfile name={info.name} avatarUrl={info.avatarUrl} />
					<MyCategoryTab category={category} setCategory={setCategory} />
					<Suspense fallback={<Loading />}>
						<S.ContentContainer>
							{category === 'article' && <ArticleBox />}
							{category === 'comment' && <CommentBox />}
							{category === 'tempArticle' && <TemporaryArticleList />}
						</S.ContentContainer>
					</Suspense>
				</>
			) : (
				<div>정보를 가져오는데 실패하였습니다</div>
			)}
		</S.Container>
	);
};

export default MyPage;
