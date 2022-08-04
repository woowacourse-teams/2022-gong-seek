import styled from '@emotion/styled';

export const Container = styled.section`
	display: flex;

	width: 100vw;
`;

export const EmptyResultBox = styled.div`
	display: flex;

	width: 100%;
	height: 100%;

	justify-content: center;
	align-items: center;
`;

export const EmptyMessage = styled.div`
	font-size: ${({ theme }) => theme.size.SIZE_016};
`;
