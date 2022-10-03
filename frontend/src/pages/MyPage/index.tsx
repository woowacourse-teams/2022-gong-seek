import React, { Suspense, useState } from 'react';

import Loading from '@/components/common/Loading/Loading';
import useGetUserInfo from '@/hooks/user/useGetUserInfo';
import CategoryTab from '@/pages/MyPage/CategoryTab/CategoryTab';
import UserProfile from '@/pages/MyPage/UserProfile/UserProfile';
import * as S from '@/pages/MyPage/index.styles';
import { CategoryType } from '@/types/myPage';

const ArticleBox = React.lazy(() => import('@/pages/MyPage/UserArticleBox/UserArticleBox'));
const CommentBox = React.lazy(() => import('@/pages/MyPage/UserCommentBox/UserCommentBox'));
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
					<CategoryTab category={category} setCategory={setCategory} />
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
