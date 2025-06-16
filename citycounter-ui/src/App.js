import React, { useState } from 'react';
import axios from 'axios';

function App() {
  const [letter, setLetter] = useState('');
  const [count, setCount] = useState(null);
  const [error, setError] = useState('');
  const [search, setSearch] = useState('');

  const handleChange = (e) => {
    setLetter(e.target.value);
  };

  const handleSubmit = async () => {
    if (letter.length < 1) {
      setError('Please enter at least  one letter.');
      return;
    }

    try {
      const response = await axios.get(`http://localhost:8080/api/city-count?letter=${letter}`);
      setSearch(letter);
      setError(''); // clear error on success
      setCount(response.data);
    } catch (error) {
      console.error('Error fetching city count', error);
      setError('Failed to fetch city count. Please try again.');
    }

  };

  return (
    <div className="p-4 max-w-md mx-auto">
      <h1 className="text-xl font-bold mb-2">City Counter</h1>
       
      {error && (
          <div className="mt-2 text-red-500 font-semibold">
            {error}  
          </div> 
      )}
     <br/>
      <input
        type="text"
        value={letter}
        onChange={handleChange}
        className="border p-2 rounded w-full"
        placeholder="Enter a letter"
      />
     
      <button
        onClick={handleSubmit}
        className="mt-2 bg-blue-500 text-white px-4 py-2 rounded"
      >
        Get City Count
      </button>
      {count !== null && (
        <p className="mt-4">Number of cities starting with "{search}": {count}</p>
      )}
    </div>
  );
}

export default App;
