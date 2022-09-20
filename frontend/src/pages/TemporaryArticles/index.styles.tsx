import styled from '@emotion/styled';

export const Container = styled.section`
	width: 100%;
	height: fit-content;
	display: flex;

	justify-content: center;
	align-items: center;
	flex-direction: column;
`;

export const Header = styled.header``;

export const Title = styled.h2``;

export const ArticleListBox = styled.div`
	width: 80%;
	margin-top: ${({ theme }) => theme.size.SIZE_030};
`;
